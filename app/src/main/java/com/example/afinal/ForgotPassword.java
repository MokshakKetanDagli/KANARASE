package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    TextInputLayout textinputlayout_emailid_forgotpassword_java;
    TextView textview_userlogin_forgotpassword_java;
    ProgressDialog progressDialog;
    Button btnresetpasswordjava;

    protected FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
        btnresetpasswordjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetlink();
            }
        });
        textview_userlogin_forgotpassword_java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this,UserLogin.class));
                finish();
            }
        });
    }
    private void resetlink(){
        final String string_emailid_forgotpassword = textinputlayout_emailid_forgotpassword_java.getEditText().getText().toString().trim();
        if (string_emailid_forgotpassword.isEmpty()) {
            textinputlayout_emailid_forgotpassword_java.setError("Fields Cannot Be Empty");
        } else {
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            firebaseAuth.sendPasswordResetEmail(string_emailid_forgotpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPassword.this, "RESET LINK SEND TO\n( " + string_emailid_forgotpassword.toUpperCase() + " )", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(ForgotPassword.this, UserLogin.class));
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPassword.this, "FAILED TO GENERATE RESET LINK", Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "ACCOUNT DOES NOT EXIST", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void init() {

        textinputlayout_emailid_forgotpassword_java = findViewById(R.id.textinputlayout_emailid_forgotpassword_xml);
        textview_userlogin_forgotpassword_java = findViewById(R.id.textview_userlogin_forgotpassword_xml);
        progressDialog = new ProgressDialog(ForgotPassword.this);
        btnresetpasswordjava = findViewById(R.id.buttonresetpassword_successxml);
        firebaseAuth = FirebaseAuth.getInstance();
    }
}
