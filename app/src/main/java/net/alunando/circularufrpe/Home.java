package net.alunando.circularufrpe;

import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;

public class Home extends AppCompatActivity {

    String localTemp="";
    CountDownTimer myCountDownTimer, myCountDownTimer2;

    ImageView localZootec, localCentral, localCegoe, localCeagri, bgOndeEsta;
    ImageView btnRefresh, bgOndeEsta2, spotted, btnMenu;

    GifImageView loading;

    TextView txtVistoNo, txtVistoHora, txtPrevLocal, txtPrevTempo, versao;

    TextView txtSalada, txtRefeicaoData, txtRefeicaoTipo, txtGuarnicao, txtPrincipal, txtSobremesa, txtSuco, txtFast, txtVegetariano, txtGrelha;

    int controlMenu=0, controlSpotted=0;

    private String HOST = "http://alunando.net/android/circular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        if (screenSize() < 720){
            setContentView(R.layout.activity_home_small);
        } else {
            setContentView(R.layout.activity_home);
        }

        atualizaAutomatico();

        versao = (TextView) findViewById(R.id.versao);

        btnMenu = (ImageView) findViewById(R.id.btnMenu);

        spotted = (ImageView) findViewById(R.id.spotted);

        loading = (GifImageView) findViewById(R.id.loading);

        btnRefresh = (ImageView) findViewById(R.id.btnRefresh);
        bgOndeEsta2 = (ImageView) findViewById(R.id.bgOndeEsta2);

        localCeagri = (ImageView) findViewById(R.id.localCeagri);
        localCegoe = (ImageView) findViewById(R.id.localCegoe);
        localCentral = (ImageView) findViewById(R.id.localCentral);
        localZootec = (ImageView) findViewById(R.id.localZootec);
        bgOndeEsta = (ImageView) findViewById(R.id.bgOndeEsta);

        localCeagri.setVisibility(View.INVISIBLE);
        localCegoe.setVisibility(View.INVISIBLE);
        localCentral.setVisibility(View.INVISIBLE);
        localZootec.setVisibility(View.INVISIBLE);
        bgOndeEsta2.setVisibility(View.INVISIBLE);

        loading.setVisibility(View.INVISIBLE);


        String vers = versao.getText().toString();
        versao.setText(vers + BuildConfig.VERSION_NAME);

        //Métodos recebem a variável auto para quando atualizar automáticamente não ficar irritando o usuário com mensagens na tela o tempo to do
        atualizaRU(1);
        atualizaCircular(1);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "EM DESENVOLVIMENTO", Toast.LENGTH_SHORT).show();
            }
        });

        spotted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreSpotted = new Intent(Home.this, Spotted.class);
                startActivity(abreSpotted);
            }
        });

        bgOndeEsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localCeagri.setVisibility(View.VISIBLE);
                localCegoe.setVisibility(View.VISIBLE);
                localCentral.setVisibility(View.VISIBLE);
                localZootec.setVisibility(View.VISIBLE);

                bgOndeEsta2.setVisibility(View.VISIBLE);
                controlMenu = 1;
                bgOndeEsta.setVisibility(View.INVISIBLE);
            }
        });

        bgOndeEsta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localCeagri.setVisibility(View.INVISIBLE);
                localCegoe.setVisibility(View.INVISIBLE);
                localCentral.setVisibility(View.INVISIBLE);
                localZootec.setVisibility(View.INVISIBLE);

                bgOndeEsta.setVisibility(View.VISIBLE);
                controlMenu = 0;
                bgOndeEsta2.setVisibility(View.INVISIBLE);
            }
        });

        localCeagri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Enviando, aguarde...", Toast.LENGTH_SHORT).show();
                enviaDados("Ceagri");
                localCeagri.setVisibility(View.INVISIBLE);
                localCegoe.setVisibility(View.INVISIBLE);
                localCentral.setVisibility(View.INVISIBLE);
                localZootec.setVisibility(View.INVISIBLE);

                bgOndeEsta.setVisibility(View.VISIBLE);
                controlMenu = 0;
                bgOndeEsta2.setVisibility(View.INVISIBLE);
            }
        });
        localCegoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Enviando, aguarde...", Toast.LENGTH_SHORT).show();
                enviaDados("Cegoe");
                localCeagri.setVisibility(View.INVISIBLE);
                localCegoe.setVisibility(View.INVISIBLE);
                localCentral.setVisibility(View.INVISIBLE);
                localZootec.setVisibility(View.INVISIBLE);

                bgOndeEsta.setVisibility(View.VISIBLE);
                controlMenu = 0;
                bgOndeEsta2.setVisibility(View.INVISIBLE);
            }
        });
        localCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Enviando, aguarde...", Toast.LENGTH_SHORT).show();
                enviaDados("Central");
                localCeagri.setVisibility(View.INVISIBLE);
                localCegoe.setVisibility(View.INVISIBLE);
                localCentral.setVisibility(View.INVISIBLE);
                localZootec.setVisibility(View.INVISIBLE);

                bgOndeEsta.setVisibility(View.VISIBLE);
                controlMenu = 0;
                bgOndeEsta2.setVisibility(View.INVISIBLE);
            }
        });
        localZootec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Enviando, aguarde...", Toast.LENGTH_SHORT).show();
                enviaDados("Zootec");
                localCeagri.setVisibility(View.INVISIBLE);
                localCegoe.setVisibility(View.INVISIBLE);
                localCentral.setVisibility(View.INVISIBLE);
                localZootec.setVisibility(View.INVISIBLE);

                bgOndeEsta.setVisibility(View.VISIBLE);
                controlMenu = 0;
                bgOndeEsta2.setVisibility(View.INVISIBLE);
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaCircular(0);
                atualizaRU(0);
                previsao();

            }
        });
        // Deixei essa outra forma de fazer com classe para lembrar, mas a loga abaixo está funcionando
        // ATT - NADA FUNCIONA NESSA PORRA!!!! ATUALIZA NA MAO USUARIOP FDP
        //Nucleo dualCore = new Nucleo();
        //dualCore.start();
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    class Nucleo extends Thread {
        public void run() {
            //Code you want to get executed seperately then main thread.
            final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println("FUCK!");


                }
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    public void atualizaCircular(final int auto){
        String url = HOST + "/circular_recebe.php";

        // txtVistoNo = (TextView) findViewById(R.id.txtVistoNo);
        // Se transformou nesses dois: txtVistoNo e txtVistoHora
        txtVistoNo = (TextView) findViewById(R.id.txtVistoNo);
        txtVistoHora = (TextView) findViewById(R.id.txtVistoHora);

        // txtTempoChegada = (TextView) findViewById(R.id.txtTempoChegada);
        // Se transformou nesses dois: txtPrevLocal e txtPrevTempo
        txtPrevLocal = (TextView) findViewById(R.id.txtPrevLocal);
        txtPrevTempo = (TextView) findViewById(R.id.txtPrevTempo);

        if (auto==0){
            Toast.makeText(Home.this, "Atualizando, aguarde...", Toast.LENGTH_SHORT).show();
        }

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
                                if(auto==0){
                                    Toast.makeText(Home.this, "Houve um erro no php!", Toast.LENGTH_SHORT).show();
                                }
                            } else if (RETORNO.equals("SUCESSO")){

                                String local = result.get("LOCAL").getAsString();
                                String hora = result.get("HORA").getAsString();
                                txtVistoNo.setText(local);
                                String[] local2 = hora.split("(?=:)");
                                txtVistoHora.setText(local2[0]+local2[1]);

                                if(auto==0){
                                    Toast.makeText(Home.this, "Atualizado!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if(auto==0){
                                    Toast.makeText(Home.this, "Erro: 1212!", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (Exception erro){
                           if (auto==0){
                               Toast.makeText(Home.this, "Ops! Ocorreu um erro: ERRO 20 - " + erro, Toast.LENGTH_LONG).show();
                           }
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
            Toast.makeText(Home.this, "Este local já foi registrado", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Home.this, "Houve um erro no php!", Toast.LENGTH_SHORT).show();
                            } else if (RETORNO.equals("SUCESSO")){
                                Toast.makeText(Home.this, "Localização salva!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Home.this, "Erro: 1313", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception erro){
                            Toast.makeText(Home.this, "Ops! Ocorreu um erro: ERRO 21 - " + erro, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void atualizaRU(final int auto){
        String url = HOST + "/ru_recebe.php";

        txtSalada = (TextView) findViewById(R.id.txtSalada);
        txtGuarnicao = (TextView) findViewById(R.id.txtGuarnicao);
        txtPrincipal = (TextView) findViewById(R.id.txtPrincipal);
        txtSobremesa = (TextView) findViewById(R.id.txtSobremesa);
        txtSuco = (TextView) findViewById(R.id.txtSuco);
        txtFast = (TextView) findViewById(R.id.txtFast);
        txtVegetariano = (TextView) findViewById(R.id.txtVegetariano);
        txtGrelha = (TextView) findViewById(R.id.txtGrelha);
        txtRefeicaoData = (TextView) findViewById(R.id.txtRefeicaoData);
        txtRefeicaoTipo = (TextView) findViewById(R.id.txtRefeicaoTipo);

        Date diaAtual = new Date();
        SimpleDateFormat diaFormatado = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat horaRuFormatado = new SimpleDateFormat("H");
        String data = diaFormatado.format(diaAtual);
        int horaRu = Integer.parseInt(horaRuFormatado.format(diaAtual));
        String tipo;

        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DATE, +1);
        Date proxDia = new Date();
        proxDia = calendario.getTime();

        if (horaRu < 14){
            tipo = "Almoço";
        } else if (horaRu < 19){
            tipo = "Jantar";
        } else {
            tipo = "Almoço";
            data = diaFormatado.format(proxDia);
        }

        Ion.with(Home.this)
                .load(url)
                .setBodyParameter("data_app", data)
                .setBodyParameter("tipo_app", tipo)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            //Toast.makeText(MainActivity.this, "Salvo: " +result.get("SALVO").getAsString(), Toast.LENGTH_LONG).show();
                            String RETORNO = result.get("RECEBE").getAsString();

                            if (RETORNO.equals("ERRO")){
                                if(auto==0){
                                    Toast.makeText(Home.this, "Houve um erro no php!", Toast.LENGTH_LONG).show();
                                }
                            } else if (RETORNO.equals("SUCESSO")){

                                txtSalada.setText(result.get("SALADA").getAsString());
                                txtGuarnicao.setText(result.get("GUARNICAO").getAsString());
                                txtPrincipal.setText(result.get("PRINCIPAL").getAsString());
                                txtSobremesa.setText(result.get("SOBREMESA").getAsString());
                                txtSuco.setText(result.get("SUCO").getAsString());
                                txtFast.setText(result.get("FASTGRILL").getAsString());
                                txtVegetariano.setText(result.get("VEGETARIANO").getAsString());
                                txtGrelha.setText(result.get("GRELHA").getAsString());
                                txtRefeicaoData.setText(result.get("DATA").getAsString());
                                txtRefeicaoTipo.setText(result.get("TIPO").getAsString());

                                if(auto==0){
                                    Toast.makeText(Home.this, "Atualizado!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (auto==0){
                                    Toast.makeText(Home.this, "Erro: 1212!", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (Exception erro){
                            if (auto==0){
                                Toast.makeText(Home.this, "Ops! Ocorreu um erro: ERRO 22 - " + erro, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

    }

    public int screenSize (){

        int altura = Resources.getSystem().getDisplayMetrics().heightPixels;
        int largura = Resources.getSystem().getDisplayMetrics().widthPixels;

        if (altura < largura){
            return altura;
        } else {
            return largura;
        }
    }

    public void atualizaAutomatico() {

        if (myCountDownTimer2 != null) {
            myCountDownTimer2.cancel();
        }
        myCountDownTimer2 = new CountDownTimer(600000, 5000) {

            public void onTick(long millisUntilFinished) {
                atualizaCircular(1);
                previsao();
            }

            public void onFinish() {
                //System.out.println("Automático terminou");
            }
        };
        myCountDownTimer2.start();
    }

    public void previsao(){
        txtVistoNo = (TextView) findViewById(R.id.txtVistoNo);
        txtVistoHora = (TextView) findViewById(R.id.txtVistoHora);
        txtPrevLocal = (TextView) findViewById(R.id.txtPrevLocal);
        txtPrevTempo = (TextView) findViewById(R.id.txtPrevTempo);

        final String[] tempo1 = txtVistoHora.getText().toString().split("(?<=:)");
        final String[] tempo2 = txtVistoHora.getText().toString().split("(?=:)");
        int horaVisto = Integer.valueOf(tempo2[0]);
        int minutoVisto = Integer.valueOf(tempo1[1]);
        int segundo, minuto, hora;
        Date horaAtual = new Date();
        SimpleDateFormat horaFormatada = new SimpleDateFormat("H");
        SimpleDateFormat horaFormatada2 = new SimpleDateFormat("mm");
        SimpleDateFormat horaFormatada3 = new SimpleDateFormat("ss");
        hora = Integer.valueOf(horaFormatada.format(horaAtual));
        minuto = Integer.valueOf(horaFormatada2.format(horaAtual));
        segundo = Integer.valueOf(horaFormatada3.format(horaAtual));

        int tempo=0;

        final String local = txtVistoNo.getText().toString();

        if (hora < 15 ){
            if (local.equals("Central")){
                localTemp = "Cegoe";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 240000;
            } else if (local.equals("Cegoe")){
                localTemp = "Zootec";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 420000;
            } else if (local.equals("Zootec")){
                localTemp = "Ceagri";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 1200000;
            } else if (local.equals("Ceagri")){
                localTemp = "Central";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 600000;
            } else {
                localTemp = "";
            }
        } else  if (hora < 18){
            if (local.equals("Central")){
                localTemp = "Ceagri";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 600000;
            } else if (local.equals("Ceagri")){
                localTemp = "Zootec";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 900000;
            } else if (local.equals("Zootec")){
                localTemp = "Cegoe";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 600000;
            } else if (local.equals("Cegoe")){
                localTemp = "Central";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 480000;
            } else {
                localTemp = "";
            }
        } else {
            if (local.equals("Central")){
                localTemp = "Ceagri";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 600000;
            } else if (local.equals("Ceagri")){
                localTemp = "Cegoe";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 600000;
            } else if (local.equals("Cegoe")){
                localTemp = "Central";
                tempo = (minutoVisto * 60000 + (horaVisto * 1000)) - (minuto * 60000 + (segundo * 1000)) + 600000;
            } else {
                localTemp = "";
            }
        }

        // Calculo da hora prevista para a chegada no próximo ponto

        int momentoVisto = (horaVisto * 60 * 60 * 1000) + (minutoVisto * 60 * 1000);
        int momentoChegar = momentoVisto + tempo;
        final int horaChegar = momentoChegar / 3600000;
        final int minChegar = ( momentoChegar / 60000 ) % 60;

        final String gambMinuto, gambMinuto2;

        if (minChegar == 0){
            gambMinuto = "0";
        } else {
            gambMinuto = "";
        }
        if (minChegar < 10){
            gambMinuto2 = "0";
        } else {
            gambMinuto2 = "";
        }

        // SE O TEMPO FOR MAIOR QUE 20MIN IRÁ ZERAR / GAMBIARRA = Programar forma melhor de corrigir isso
        if (tempo > 1300000){
            tempo = 0;
        }

        if (myCountDownTimer != null) {
            myCountDownTimer.cancel();
        }
        myCountDownTimer = new CountDownTimer(tempo, 1000) {

            public void onTick(long millisUntilFinished) {

                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;

                txtPrevLocal.setText("No "+ localTemp + " em");
                txtPrevTempo.setText(f.format(min) +':'+ f.format(sec) + " min");
            }

            public void onFinish() {
                txtPrevLocal.setText("No " + localTemp);
                txtPrevTempo.setText("às " + horaChegar + ":" + gambMinuto2 + minChegar + gambMinuto);
            }
        };
        myCountDownTimer.start();
    }

}
