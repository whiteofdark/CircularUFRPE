package net.alunando.circularufrpe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class Login extends AppCompatActivity {

    private EditText editEmailLogar, editSenhaLogar;
    private Button btnEntrar;
    private TextView txtCadastro;

    private String HOST = "http://alunando.net/android/circular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmailLogar = (EditText) findViewById(R.id.editEmailLogar);
        editSenhaLogar = (EditText) findViewById(R.id.editSenhaLogar);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        txtCadastro = (TextView) findViewById(R.id.txtCadastro);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nickname = editEmailLogar.getText().toString();
                String senha = editSenhaLogar.getText().toString();

                String URL = HOST + "/login.php";

                if (!nickname.isEmpty() && !senha.isEmpty()) {
                    Ion.with(Login.this)
                            .load(URL)
                            .setBodyParameter("nickname_app", nickname)
                            .setBodyParameter( "senha_app", senha)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    try {
                                        String RETORNO = result.get("LOGIN").getAsString();

                                        if (RETORNO.equals("ERRO")) {
                                            Toast.makeText(Login.this, "Ops! Login ou senha incorretos ", Toast.LENGTH_LONG).show();
                                        } else if (RETORNO.equals("SUCESSO")) {

                                            String nome = result.get("NOME").getAsString();
                                            String token = result.get("TOKEN").getAsString();

                                            SharedPreferences.Editor pref = getSharedPreferences("info", MODE_PRIVATE).edit();

                                            pref.putString("nome", nome).apply();
                                            pref.putString("token", token).apply();

                                            Intent abreHome = new Intent(Login.this, Home.class);
                                            startActivity(abreHome);

                                        } else {
                                            Toast.makeText(Login.this, "Ops! ERRO 12: ", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception erro) {
                                        Toast.makeText(Login.this, "Ops! ERRO 14: " + erro, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
            }
        });
    }
}
