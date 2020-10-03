package com.android.almg.chalchiteko;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private final String GREETER = "Hello from the other side!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btnVocabulary);
        btn2 = (Button) findViewById(R.id.btnExamples);
        btn3 = (Button) findViewById(R.id.btnEvaluation);
        btn4 = (Button) findViewById(R.id.btnLogin);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Acceder al Segundo Activity
                Intent intent = new Intent(MainActivity.this, VocabularyActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Acceder al Segundo Activity
                Intent intent = new Intent(MainActivity.this, ExamplesActivity.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Acceder al Segundo Activity
                Intent intent = new Intent(MainActivity.this, EvaluationActivity.class);
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Acceder al Segundo Activity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Tomar datos del Intent
        Bundle bundle = getIntent().getExtras();
    }
}