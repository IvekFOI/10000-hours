package com.example.matija.registration0407;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Izbor extends AppCompatActivity {


    Button New,Rez;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izbor);

        New = findViewById(R.id.New);
        Rez = findViewById(R.id.Rez);

        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Izbor.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        Rez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent (Izbor.this, Rez.class);
                startActivity(intent2);
            }
        });


    }
}
