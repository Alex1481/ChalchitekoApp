package com.android.almg.chalchiteko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TranslateActivity extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> chalchitekoList;
    ArrayList<String> spanishList;
    TranslateAdpater translateAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        //Codigo para icono en el Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Tomar datos del Intent
        Bundle bundle = getIntent().getExtras();
        search_edit_text = (EditText) findViewById(R.id.search_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        chalchitekoList = new ArrayList<>();
        spanishList = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    setAdapter(s.toString());
                }
            }
        });

    }

    private void setAdapter(final String searchedString) {
        spanishList.clear();
        chalchitekoList.clear();
        recyclerView.removeAllViews();

        databaseReference.child("word").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int counter = 0;

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    String uid = snapshot.getKey();
                    String chalchiteko = snapshot.child("chalchiteko").getValue(String.class);
                    String spanish = snapshot.child("spanish").getValue(String.class);

                    if(spanish.contains(searchedString)){
                        spanishList.add(spanish);
                        chalchitekoList.add(chalchiteko);
                        counter++;
                    } else if (chalchiteko.contains(searchedString)){
                        spanishList.add(spanish);
                        chalchitekoList.add(chalchiteko);
                        counter++;
                    }

                    if (counter == 15)
                        break;
                }

                translateAdpater = new TranslateAdpater(TranslateActivity.this, spanishList, chalchitekoList);
                recyclerView.setAdapter(translateAdpater);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}