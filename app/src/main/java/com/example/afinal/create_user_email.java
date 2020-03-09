package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class create_user_email extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    Button btncreateuseremailjava;
    ProgressDialog progressDialog;
    TextInputLayout textinputlayout_fullname_createuser_java, textinputlayout_dob_createuser_java, textinputlayout_emailid_createuser_java, textinputlayout_password_createuser_java, textinputlayout_confirmpassword_createuser_java;
    TextView tvuserloginjava;

    protected FirebaseDatabase firebaseDatabase;
    protected DatabaseReference databaseReference;
    protected FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_email);
        init();

        btncreateuseremailjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readandcreateuser();
            }
        });
        tvuserloginjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(create_user_email.this, UserLogin.class));
                finish();
            }
        });
    }

    private void init() {
        textinputlayout_fullname_createuser_java = findViewById(R.id.textinputlayout_fullname_createuseremail_xml);
        textinputlayout_dob_createuser_java = findViewById(R.id.textinputlayout_dob_createuseremail_xml);
        textinputlayout_emailid_createuser_java = findViewById(R.id.textinputlayout_emailid_createuseremail_xml);
        textinputlayout_password_createuser_java = findViewById(R.id.textinputlayout_pasword_createuseremail_xml);
        textinputlayout_confirmpassword_createuser_java = findViewById(R.id.textinputlayout_confirmpassword_createuseremail_xml);
        progressDialog = new ProgressDialog(create_user_email.this);
        btncreateuseremailjava = findViewById(R.id.buttoncreateuseremail_successxml);
        tvuserloginjava = findViewById(R.id.textviewuserloginemailpagexml);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("USERS/USERS_EMAIL");
    }

    private void readandcreateuser() {
        final String string_fullname_createuser = textinputlayout_fullname_createuser_java.getEditText().getText().toString().trim();
        final String string_dob_createuser = textinputlayout_dob_createuser_java.getEditText().getText().toString().trim();
        final String string_emailid_createuser = textinputlayout_emailid_createuser_java.getEditText().getText().toString().trim();
        String string_password_createuser = textinputlayout_password_createuser_java.getEditText().getText().toString().trim();
        String string_confirmpassword_createuser = textinputlayout_confirmpassword_createuser_java.getEditText().getText().toString().trim();

        if (string_fullname_createuser.isEmpty() && string_dob_createuser.isEmpty() && string_emailid_createuser.isEmpty() && string_password_createuser.isEmpty() && string_confirmpassword_createuser.isEmpty()) {
            textinputlayout_fullname_createuser_java.setError("Fields Cannot Be Empty");
            textinputlayout_dob_createuser_java.setError("Fields Cannot Be Empty");
            textinputlayout_emailid_createuser_java.setError("Fields Cannot Be Empty");
            textinputlayout_password_createuser_java.setError("Fields Cannot Be Empty");
            textinputlayout_confirmpassword_createuser_java.setError("Fields Cannot Be Empty");
        } else if (!(string_fullname_createuser.isEmpty() && string_dob_createuser.isEmpty() && string_emailid_createuser.isEmpty() && string_password_createuser.isEmpty() && string_confirmpassword_createuser.isEmpty())) {
            if (!(string_dob_createuser.length() < 10)) {
                if (Patterns.EMAIL_ADDRESS.matcher(string_emailid_createuser).matches()) {
                    if (PASSWORD_PATTERN.matcher(string_password_createuser).matches()) {
                        if (string_confirmpassword_createuser.equals(string_password_createuser)) {
                            progressDialog.show();
                            progressDialog.setContentView(R.layout.progress_dialog);
                            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            firebaseAuth.createUserWithEmailAndPassword(string_emailid_createuser, string_password_createuser).addOnCompleteListener(create_user_email.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(create_user_email.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(create_user_email.this, "Please Verify Your Email", Toast.LENGTH_SHORT).show();
                                                    Users users = new Users(string_fullname_createuser, string_dob_createuser, string_emailid_createuser);
                                                    String key = databaseReference.push().getKey();
                                                    databaseReference.child(key).setValue(users).addOnCompleteListener(create_user_email.this, new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(create_user_email.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                                                progressDialog.dismiss();
                                                                startActivity(new Intent(create_user_email.this, UserLogin.class));
                                                                finish();
                                                            } else {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(create_user_email.this, "User Creation Failed", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }).addOnFailureListener(create_user_email.this, new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(create_user_email.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(create_user_email.this, "Failed To Send Email Verification Link", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(create_user_email.this, new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(create_user_email.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(create_user_email.this, "Failed To Create User", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(create_user_email.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            textinputlayout_confirmpassword_createuser_java.setError("Passwords Does Not Match");
                        }
                    } else {
                        textinputlayout_password_createuser_java.setError("Too Weak Password\nAtleast 1 UpperCase,1 LowerCase,1 Number And Min 6 Character");
                        textinputlayout_confirmpassword_createuser_java.setError("Too Weak Password\nAtleast 1 UpperCase,1 LowerCase,1 Number And Min 6 Character");
                    }
                } else {
                    textinputlayout_emailid_createuser_java.setError("Please Enter A Valid Email Address\nEg : abcd@xyz.com");
                }
            } else {
                textinputlayout_fullname_createuser_java.setError("Please Enter A Valid DOB\nEx : DD-MM-YYYY");
            }
        } else {
            Toast.makeText(this, "Please Enter The Values", Toast.LENGTH_SHORT).show();
        }
    }
}
