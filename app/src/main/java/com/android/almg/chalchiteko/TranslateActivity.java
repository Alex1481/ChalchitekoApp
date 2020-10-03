package com.android.almg.chalchiteko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TranslateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        //Tomar datos del Intent
        Bundle bundle = getIntent().getExtras();
    }
}