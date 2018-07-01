package net.alunando.circularufrpe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import net.alunando.circularufrpe.classes.Comentario;
import net.alunando.circularufrpe.classes.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SpottedComments extends AppCompatActivity {

    ImageView btnVolta;
    RelativeLayout divComment;
    Button btnEnvia;
    TextView txtComentarioPostado_1, txtNome_1, txtData_1;
    EditText txtComentario;

    Usuario usuarioLogado = new Usuario();
    Comentario comentario = new Comentario();

    private String HOST = "http://alunando.net/android/circular";

    boolean retorno;

    int contadorComment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_spotted_comments);

        btnVolta = (ImageView) findViewById(R.id.btnVolta);
        divComment = (RelativeLayout) findViewById(R.id.divComment);
        btnEnvia = (Button) findViewById(R.id.btnEnvia);
        txtComentarioPostado_1 = (TextView) findViewById(R.id.txtComentarioPostado_1);
        txtNome_1 = (TextView) findViewById(R.id.txtNome_1);
        txtData_1 = (TextView) findViewById(R.id.txtData_1);

        Intent recebendoDados = getIntent();
        Bundle dados = recebendoDados.getExtras();
        usuarioLogado = (Usuario) dados.getSerializable("usuario");

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
        divComment.setVisibility(View.INVISIBLE);
        divComment.setVisibility(View.GONE);

        btnEnvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

                ArrayList<View> rootView = new ArrayList<View>();

                rootView.add(inflater.inflate(R.layout.layout_comment, null));
                FrameLayout container = (FrameLayout) findViewById(R.id.campoComentarios);

                container.addView(rootView.get(contadorComment));
                contadorComment++;

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
                                Toast.makeText(SpottedComments.this, "Ops! ERRO 34: " + erro, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }

        return retorno;
    }

}