package net.alunando.circularufrpe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Perfil extends AppCompatActivity {

    TextView txtNome, txtNickname, txtEmail, txtAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtNome = (TextView) findViewById(R.id.txtNome);
        txtNickname = (TextView) findViewById(R.id.txtNickname);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtAvatar = (TextView) findViewById(R.id.txtAvatar);

        if (verificaDadosLocais()){
            SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);

            txtNome.setText(pref.getString("nome", ""));
            txtNickname.setText(pref.getString("nickname", ""));
            txtEmail.setText(pref.getString("email", ""));
            txtAvatar.setText(pref.getString("avatar", ""));
        }
    }

    public boolean verificaDadosLocais() {

        SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);

        String token = pref.getString("token", "");

        if(!token.isEmpty()) {
            return true;
        } else {
            Toast.makeText(Perfil.this, "Você não está logado", Toast.LENGTH_SHORT).show();
            Intent abreHome = new Intent(Perfil.this, Home.class);
            startActivity(abreHome);
            return false;
        }

    }
}

