package com.android.almg.chalchiteko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private View btn;
    private View btn2;
    private final String GREETER = "Hello from the other side!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btnVocabulary);
        btn2 = (Button) findViewById(R.id.btnExamples);

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
    }
}