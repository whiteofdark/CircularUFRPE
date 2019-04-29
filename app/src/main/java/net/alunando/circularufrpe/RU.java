package net.alunando.circularufrpe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RU extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ru);
    }
}
