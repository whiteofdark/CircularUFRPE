package net.alunando.circularufrpe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Home extends AppCompatActivity {

    ImageView localZootec, localCentral, localCegoe, localCeagri, bgOndeEsta;
    ImageView btnRefresh;

    TextView txtVistoNo, txtVistoHora, txtPrevLocal, txtPrevTempo;

    int controlMenu=0;

    private String HOST = "http://alunando.net/android/circular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        btnRefresh = (ImageView) findViewById(R.id.btnRefresh);

        localCeagri = (ImageView) findViewById(R.id.localCeagri);
        localCegoe = (ImageView) findViewById(R.id.localCegoe);
        localCentral = (ImageView) findViewById(R.id.localCentral);
        localZootec = (ImageView) findViewById(R.id.localZootec);
        bgOndeEsta = (ImageView) findViewById(R.id.bgOndeEsta);

        localCeagri.setVisibility(View.INVISIBLE);
        localCegoe.setVisibility(View.INVISIBLE);
        localCentral.setVisibility(View.INVISIBLE);
        localZootec.setVisibility(View.INVISIBLE);

        try {
            new Thread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        atualizaCircular();

        bgOndeEsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controlMenu == 0){
                    localCeagri.setVisibility(View.VISIBLE);
                    localCegoe.setVisibility(View.VISIBLE);
                    localCentral.setVisibility(View.VISIBLE);
                    localZootec.setVisibility(View.VISIBLE);
                    controlMenu = 1;
                } else {
                    localCeagri.setVisibility(View.INVISIBLE);
                    localCegoe.setVisibility(View.INVISIBLE);
                    localCentral.setVisibility(View.INVISIBLE);
                    localZootec.setVisibility(View.INVISIBLE);
                    controlMenu = 0;
                }
            }
        });

        localCeagri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaDados("Ceagri");
            }
        });
        localCegoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaDados("Cegoe");
            }
        });
        localCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaDados("Central");
            }
        });
        localZootec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaDados("Zootec");
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaCircular();

                try {
                    new Thread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    public void atualizaCircular(){
        String url = HOST + "/circular_recebe.php";

        // txtVistoNo = (TextView) findViewById(R.id.txtVistoNo);
        // Se transformou nesses dois: txtVistoNo e txtVistoHora
        txtVistoNo = (TextView) findViewById(R.id.txtVistoNo);
        txtVistoHora = (TextView) findViewById(R.id.txtVistoHora);

        // txtTempoChegada = (TextView) findViewById(R.id.txtTempoChegada);
        // Se transformou nesses dois: txtPrevLocal e txtPrevTempo
        txtPrevLocal = (TextView) findViewById(R.id.txtPrevLocal);
        txtPrevTempo = (TextView) findViewById(R.id.txtPrevTempo);

        Ion.with(Home.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            //Toast.makeText(MainActivity.this, "Salvo: " +result.get("SALVO").getAsString(), Toast.LENGTH_LONG).show();
                            String RETORNO = result.get("RECEBE").getAsString();

                            if (RETORNO.equals("ERRO")){
                                Toast.makeText(Home.this, "Houve um erro no php!", Toast.LENGTH_LONG).show();
                            } else if (RETORNO.equals("SUCESSO")){

                                String local = result.get("LOCAL").getAsString();
                                String hora = result.get("HORA").getAsString();
                                txtVistoNo.setText(local);
                                String[] local2 = hora.split("(?=:)");
                                txtVistoHora.setText(local2[0]+local2[1]);

                                Toast.makeText(Home.this, "Atualizado!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Home.this, "Erro: 1212!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception erro){
                            Toast.makeText(Home.this, "Ops! Ocorreu um erro" + erro, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void enviaDados (String local) {
        String url = HOST + "/circular_envia.php";
        String hora;

        txtVistoNo = (TextView) findViewById(R.id.txtVistoNo);

        final String local2 = txtVistoNo.getText().toString();

        if (local2.equals(local)){
            Toast.makeText(Home.this, "Este local já foi registrado", Toast.LENGTH_LONG).show();
            return;
        }

        Date agora = new Date();
        SimpleDateFormat dataFormatada = new SimpleDateFormat("H:mm:ss");

        hora = dataFormatada.format(agora);

        Ion.with(Home.this)
                .load(url)
                .setBodyParameter("local_app", local)
                .setBodyParameter("hora_app", hora)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            //Toast.makeText(MainActivity.this, "Salvo: " +result.get("SALVO").getAsString(), Toast.LENGTH_LONG).show();
                            String RETORNO = result.get("SALVO").getAsString();

                            if (RETORNO.equals("ERRO")){
                                Toast.makeText(Home.this, "Houve um erro no php!", Toast.LENGTH_LONG).show();
                            } else if (RETORNO.equals("SUCESSO")){
                                Toast.makeText(Home.this, "Localização salva!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Home.this, "Erro: 1313", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception erro){
                            Toast.makeText(Home.this, "Ops! Ocorreu um erro" + erro, Toast.LENGTH_LONG).show();
                        }
                    }
                });

        try {
            new Thread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        atualizaCircular();
    }
}
