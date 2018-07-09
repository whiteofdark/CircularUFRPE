package net.alunando.circularufrpe;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Intent;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.alunando.circularufrpe.imageUpload.FilePath;
import net.alunando.circularufrpe.imageUpload.HttpRequestImageLoadTask;
import net.alunando.circularufrpe.imageUpload.HttpRequestLongOperation;

public class Cadastro extends AppCompatActivity {

    private final String HOST = "http://alunando.net/android/circular";

    /* Api variables */
    String websiteURL   = "http://alunando.net/android/circular";
    String apiURL       = "http://alunando.net/android/circular/api"; // Without ending slash
    String apiPassword  = "qw2e3erty2@1!24$";

    /* Current image */
    String currentImagePath = "";
    String currentImage = "";

    private EditText editNomeCad, editUsuarioCad, editEmailCad, editSenhaCad, editConfSenhaCad;
    private ImageView btnRegistrarUsuario;

    String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (screenSize() < 720){
            setContentView(R.layout.activity_cadastro_small);
        } else {
            setContentView(R.layout.activity_cadastro);
        }

        getSupportActionBar().hide();

        ImageView btnCarregarFoto = (ImageView) findViewById(R.id.btnCarregarFoto);
        btnRegistrarUsuario = (ImageView) findViewById(R.id.btnRegistrarUsuario);

        btnCarregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUpload();
            }
        });

        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();
            }
        });

    }

    /*- Button Listener ------------------------------------------------------------- */
    public void imageUpload() {
        // Load image
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            // Set image
            //ImageView imageViewImage = (ImageView)findViewById(R.id.imageViewImage);
            //imageViewImage.setImageURI(selectedImageUri);

            // Save image
            String destinationFilename = FilePath.getPath(Cadastro.this, selectedImageUri);

            // Dynamic text
            TextView textViewDynamicText = (TextView) findViewById(R.id.textViewDynamicText); // Dynamic text

            // URL
            String urlToApi = apiURL + "/image_upload.php";


            // Toast
            //Toast.makeText(this, "ID:"  + currentRecipeId, Toast.LENGTH_LONG).show();

            // Data
            Map mapData = new HashMap();
            mapData.put("inp_api_password", apiPassword);

            HttpRequestLongOperation task = new HttpRequestLongOperation(Cadastro.this, urlToApi, "post_image", mapData, destinationFilename, textViewDynamicText, new HttpRequestLongOperation.TaskListener() {
                @Override
                public void onFinished(String result) {
                    // Do Something after the task has finished
                    imageUploadResult();
                }
            });
            task.execute();

        }
    }

    public void imageUploadResult() {
        // Dynamic text
        TextView textViewDynamicText = (TextView)findViewById(R.id.textViewDynamicText);
        String dynamicText = textViewDynamicText.getText().toString();

        // Split
        int index = dynamicText.lastIndexOf('/');
        try {
            currentImagePath = dynamicText.substring(0, index);
        }
        catch (Exception e){
            Toast.makeText(Cadastro.this, "path: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            currentImage = dynamicText.substring(index,dynamicText.length());
        }
        catch (Exception e){
            Toast.makeText(Cadastro.this, "image: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // Load new image
        loadImage();

    } // imageUploadResult

    /*- Load image ------------------------------------------------------------------ */
    public void loadImage(){

        // Load image
        ImageView imageViewImage = (ImageView)findViewById(R.id.imageViewImage);

        if(!(currentImagePath.equals("")) && !(currentImage.equals(""))){

            String loadImage = websiteURL + "/gamb/" + currentImagePath + "/" + currentImage;

            avatar = websiteURL + "/avatares" + currentImage;

            new HttpRequestImageLoadTask(Cadastro.this, loadImage, imageViewImage).execute();

        }
    }

    //melhorar código passando objeto de usuário para este método, talvez usar outro método para criar o objeto
    public void cadastrarUsuario(){
        String URL = HOST + "/cadastro_usuario.php";

        editNomeCad = (EditText) findViewById(R.id.editNomeCad);
        editUsuarioCad = (EditText) findViewById(R.id.editUsuarioCad);
        editEmailCad = (EditText) findViewById(R.id.editEmailCad);
        editSenhaCad = (EditText) findViewById(R.id.editSenhaCad);
        editConfSenhaCad = (EditText) findViewById(R.id.editConfSenhaCad);

        final String nome = editNomeCad.getText().toString();
        final String nickname = editUsuarioCad.getText().toString();
        final String email = editEmailCad.getText().toString();
        String senha = editSenhaCad.getText().toString();
        String confirma = editConfSenhaCad.getText().toString();

        String token = getRandomPass(8);

        String avatarIn = avatar;

        if(confirma.equals(senha) && !nome.isEmpty() && !email.isEmpty() && !nickname.isEmpty()) {
            Ion.with(Cadastro.this)
                    .load(URL)
                    .setBodyParameter("nome_app", nome)
                    .setBodyParameter("nickname_app", nickname)
                    .setBodyParameter("email_app", email)
                    .setBodyParameter("senha_app", senha)
                    .setBodyParameter("token_app", token)
                    .setBodyParameter("avatar_app", avatarIn)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try{
                                //Toast.makeText(MainCadastro.this, "Nome: " + result.get("NOME").getAsString(), Toast.LENGTH_LONG).show();
                                String RETORNO = result.get("CADASTRO").getAsString();

                                if(RETORNO.equals("EMAIL_ERRO")) {
                                    Toast.makeText(Cadastro.this, "Ops! Este email já está cadastrado ", Toast.LENGTH_LONG).show();
                                } else if (RETORNO.equals("SUCESSO")) {
                                    Toast.makeText(Cadastro.this, "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                                    /* Implementar essa parte para salvar as preferências assim que fazer o cadastro e já ficar logado

                                    SharedPreferences.Editor pref = getSharedPreferences("info", MODE_PRIVATE).edit();

                                    pref.putString("nome", nome);
                                    pref.putString("email", email);

                                    pref.commit();
                                    */


                                    Intent abrePrincipal = new Intent(Cadastro.this, Home.class);
                                    startActivity(abrePrincipal);
                                } else {
                                    Toast.makeText(Cadastro.this, "54: Ops! Ocorreu um erro, ", Toast.LENGTH_LONG).show();
                                }


                            } catch (Exception erro) {
                                Toast.makeText(Cadastro.this, "55: Ops! Ocorreu um erro, " + erro, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(Cadastro.this, "Senhas não coincidem", Toast.LENGTH_LONG).show();
        }
    }

    public static String getRandomPass(int len){
        char[] chart ={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] senha= new char[len];
        int chartLenght = chart.length, i=0;
        Random rdm = new Random();
        for (int x=0; x<len; x++) {
            senha[i] = chart[rdm.nextInt(chartLenght)];
            i++;
        }
        return new String(senha);
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
}
