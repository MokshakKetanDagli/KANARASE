package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainPage extends AppCompatActivity {

    ImageButton btncreateuserjava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        init();

        btncreateuserjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, create_user_email.class));
            }
        });
    }

    private void init() {
        btncreateuserjava = findViewById(R.id.buttoncreateuserxml);
    }

}
