package net.alunando.circularufrpe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import net.alunando.circularufrpe.classes.Comentario;
import net.alunando.circularufrpe.classes.ComentarioAdapter;
import net.alunando.circularufrpe.classes.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpottedComments extends AppCompatActivity {

    ImageView btnVolta;
    RelativeLayout divComment;
    Button btnEnvia;
    TextView txtComentarioPostado_1, txtNome_1, txtData_1;
    EditText txtComentario;

    Usuario usuarioLogado = new Usuario();
    Comentario comentario = new Comentario();

    final private String HOST = "http://alunando.net/android/circular";

    boolean retorno;

    RecyclerView recyclerView;
    ComentarioAdapter adapter;

    List<Comentario> comentarioList = new ArrayList<>();

    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_spotted_comments);

        btnVolta = (ImageView) findViewById(R.id.btnVolta);
        //divComment = (RelativeLayout) findViewById(R.id.divComment);
        btnEnvia = (Button) findViewById(R.id.btnEnvia);
        txtComentarioPostado_1 = (TextView) findViewById(R.id.txtComentarioPostado_1);
        txtNome_1 = (TextView) findViewById(R.id.txtNome_1);
        txtData_1 = (TextView) findViewById(R.id.txtData_1);

        Intent recebendoDados = getIntent();
        Bundle dados = recebendoDados.getExtras();
        usuarioLogado = (Usuario) dados.getSerializable("usuario");

        recebeComments();

        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreSpotted = new Intent(SpottedComments.this, Spotted.class);
                startActivity(abreSpotted);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        // GAMBIARRA: Aqui os comentários já estão criados no XML, apenas os escondo e mostro novamente com os novos dados, assim que alguém comentar
        // Corrigir fazendo a criação de novas partes do layout em tempo de execução
        //divComment.setVisibility(View.INVISIBLE);
        //divComment.setVisibility(View.GONE);



        btnEnvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //adicionando comentarios

                adicionaComentario();
                Toast.makeText(SpottedComments.this, "Enviando comentário...", Toast.LENGTH_SHORT).show();
                enviaDados();
                txtComentario.setText("");

                /** Segunda tentativa mas que não consegui alimentar a lista além do primeiro elemento
                 //..

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

                ArrayList<View> rootView = new ArrayList<View>();

                rootView.add(inflater.inflate(R.layout.layout_comment, null));
                FrameLayout container = (FrameLayout) findViewById(R.id.campoComentarios);

                container.addView(rootView.get(contadorComment));
                contadorComment++;

                 **/

                /**
                carregaComment();
                if (enviaDados()){
                    txtComentarioPostado_1.setText(comentario.getTexto());
                    txtNome_1.setText(comentario.getUsuario().getNome());
                    txtData_1.setText((comentario.getData() + " às " + comentario.getHora()));

                    divComment.setVisibility(View.VISIBLE);
                }
                **/
            }
        });

    }

    public void recebeComments(){

        String URL = HOST + "/comentario_recebe.php";

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

        comentario = carregaComment();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray comments = new JSONArray(response);

                            for (int i=0; i < comments.length(); i++){
                                JSONObject commentsObject = comments.getJSONObject(i);

                                String user = commentsObject.getString("USUARIO");
                                String hora = commentsObject.getString("HORA");
                                String texto = commentsObject.getString("TEXTO");

                                // Criar chave estrangeira para capturar o nome e link do avatar do usuario na query do comentario
                                //String image = commentsObject.getString("AVATAR");

                                comentario = new Comentario();
                                carregaComment();

                                comentario.getUsuario().setNome(user);
                                comentario.setHora(hora);
                                comentario.setTexto(texto);

                                comentarioList.add(comentario);
                            }

                            adapter = new ComentarioAdapter(SpottedComments.this, comentarioList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SpottedComments.this,"Erro ao carregar comentarios!", Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void adicionaComentario(){

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager((new LinearLayoutManager(this)));

        comentario = carregaComment();
        comentarioList.add(comentario);
        adapter = new ComentarioAdapter(this, comentarioList);
        recyclerView.setAdapter(adapter);
    }

    public Comentario carregaComment(){

        txtComentario = (EditText) findViewById(R.id.txtComentario);

        Date agora = new Date();
        SimpleDateFormat horaFormatada = new SimpleDateFormat("H:mm:ss");
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd:MM:yy");

        String data = dataFormatada.format(agora);
        String hora = horaFormatada.format(agora);

        comentario.setUsuario(usuarioLogado);
        comentario.setTexto(txtComentario.getText().toString());
        comentario.setData(data);
        comentario.setHora(hora);

        return comentario;
    }

    public boolean enviaDados (){
        String URL = HOST + "/comentario_envia.php";

        if (!comentario.getTexto().isEmpty() && !comentario.getUsuario().getNome().isEmpty()) {
            Ion.with(SpottedComments.this)
                    .load(URL)
                    .setBodyParameter("id_app", Integer.toString(comentario.getUsuario().getId()))
                    .setBodyParameter( "texto_app", comentario.getTexto())
                    .setBodyParameter( "data_app", comentario.getData())
                    .setBodyParameter( "hora_app", comentario.getHora())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                String RETORNO = result.get("LOGIN").getAsString();

                                if (RETORNO.equals("ERRO")) {
                                    Toast.makeText(SpottedComments.this, "Ops! Não foi possível enviar o comentário", Toast.LENGTH_LONG).show();
                                    retorno = false;
                                } else if (RETORNO.equals("SUCESSO")) {

                                retorno = true;

                                } else {
                                    Toast.makeText(SpottedComments.this, "Ops! ERRO 32: ", Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception erro) {
                                //ta mostrando esse erro mas está funcionando. Solução? para de moestrar o erro, kkk
                                //Toast.makeText(SpottedComments.this, "Ops! ERRO 34: " + erro, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }

        this.recreate();
        return retorno;
    }

}