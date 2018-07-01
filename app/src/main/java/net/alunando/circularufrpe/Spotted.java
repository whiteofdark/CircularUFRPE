package net.alunando.circularufrpe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.alunando.circularufrpe.classes.Usuario;

public class Spotted extends AppCompatActivity {

    TextView clickComments;
    ImageView btnVolta;

    Usuario usuarioLogado = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_spotted);

        btnVolta = (ImageView) findViewById(R.id.btnVolta);
        clickComments = (TextView) findViewById(R.id.clickComments);

        Intent recebendoDados = getIntent();
        Bundle dados = recebendoDados.getExtras();
        usuarioLogado = (Usuario) dados.getSerializable("usuario");

        clickComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreCommets = new Intent(Spotted.this, SpottedComments.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuarioLogado);
                abreCommets.putExtras(bundle);

                startActivity(abreCommets);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreHome = new Intent(Spotted.this, Home.class);
                startActivity(abreHome);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
    }
}
