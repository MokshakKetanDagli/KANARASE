package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class new_account_format extends AppCompatActivity {

    Button btncreateuseremailpagejava,btncreateuserphonepagejava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_format);
        init();
        btncreateuseremailpagejava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(new_account_format.this, create_user_email.class));
            }
        });
        btncreateuserphonepagejava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(new_account_format.this,create_user_phone.class));
            }
        });
    }

    private void init() {
        btncreateuseremailpagejava = findViewById(R.id.buttoncreateuseremailpagexml);
        btncreateuserphonepagejava = findViewById(R.id.buttoncreateuserphonepagexml);
    }
}
