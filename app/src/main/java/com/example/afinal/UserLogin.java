package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class UserLogin extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    TextInputLayout textinputlayout_emailid_userlogin_java, textinputlayout_password_userlogin_java;
    ProgressDialog progressDialog;
    Button btnloginuserpagejava;
    TextView tvnewuserjava, tvrforgotpasswordjava;

    protected FirebaseDatabase firebaseDatabase;
    protected DatabaseReference databaseReference;
    protected FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        init();

        btnloginuserpagejava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyuser();
            }
        });
        tvnewuserjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, new_account_format.class));
            }
        });
        tvrforgotpasswordjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, ForgotPassword.class));
            }
        });
    }

    private void verifyuser() {
        String string_emailid_userlogin = textinputlayout_emailid_userlogin_java.getEditText().getText().toString().trim();
        String string_password_userlogin = textinputlayout_password_userlogin_java.getEditText().getText().toString().trim();
        if (string_emailid_userlogin.isEmpty() && string_password_userlogin.isEmpty()) {
            textinputlayout_emailid_userlogin_java.setError("Fields Cannot Be Empty");
            textinputlayout_password_userlogin_java.setError("Fields Cannot Be Empty");
        } else if (!(string_emailid_userlogin.isEmpty() && string_password_userlogin.isEmpty())) {
            if (Patterns.EMAIL_ADDRESS.matcher(string_emailid_userlogin).matches()) {
                if (PASSWORD_PATTERN.matcher(string_password_userlogin).matches()) {
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    firebaseAuth.signInWithEmailAndPassword(string_emailid_userlogin, string_password_userlogin).addOnCompleteListener(UserLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(UserLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    startActivity(new Intent(UserLogin.this, UserPage.class));
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(UserLogin.this, "Email Not Verified", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(UserLogin.this, "Login Failed\nPlease Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(UserLogin.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UserLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    textinputlayout_password_userlogin_java.setError("Password Too Weak");
                }
            } else {
                textinputlayout_emailid_userlogin_java.setError("Please Enter A Valid Email Address");
            }
        } else {
            Toast.makeText(this, "Please Enter The Values", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        textinputlayout_emailid_userlogin_java = findViewById(R.id.textinputlayout_emailid_userlogin_xml);
        textinputlayout_password_userlogin_java = findViewById(R.id.textinputlayout_password_userlogin_xml);
        btnloginuserpagejava = findViewById(R.id.buttonloginuseremail_successxml);
        tvnewuserjava = findViewById(R.id.textviewnewuserxml);
        tvrforgotpasswordjava = findViewById(R.id.textviewforgotpasswordxml);
        progressDialog = new ProgressDialog(UserLogin.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("USERS");
        firebaseAuth = FirebaseAuth.getInstance();
    }
}
