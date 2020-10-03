package com.android.almg.chalchiteko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VocabularyActivity extends AppCompatActivity {

    private Button btn;
    private Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        btn = (Button) findViewById(R.id.btnView);
        btn2 = (Button) findViewById(R.id.btnTranslate);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Acceder al Segundo Activity
                Intent intent = new Intent(VocabularyActivity.this, ExamplesActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Acceder al Segundo Activity
                Intent intent = new Intent(VocabularyActivity.this, TranslateActivity.class);
                startActivity(intent);
            }
        });

        //Tomar datos del Intent
        Bundle bundle = getIntent().getExtras();


    }
}