package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.arch.core.executor.TaskExecutor;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class create_user_phone extends AppCompatActivity {

    private TextInputLayout textinputlayout_fullname_createuser_java, textinputlayout_dob_createuser_java, textinputlayout_phone_createuser_java, textinputlayout_otp_createuser_java;
    private TextView tvuserloginjava;
    private Button btnsendotpphonejava, btnverifyotpphonejava;

    private Spinner spinner;

    protected FirebaseDatabase firebaseDatabase;
    protected DatabaseReference databaseReference;
    protected FirebaseAuth firebaseAuth;

    private String verificationid;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_phone);

        init();

        tvuserloginjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(create_user_phone.this, UserLogin.class));
                finish();
            }
        });

        btnsendotpphonejava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyuserdata();
            }
        });

        btnverifyotpphonejava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_code = textinputlayout_otp_createuser_java.getEditText().getText().toString().trim();
                if (user_code.isEmpty() || user_code.length() < 6) {
                    textinputlayout_otp_createuser_java.setError("Please Enter A Valid OTP");
                } else {
                    verifycode(user_code);
                }
            }
        });
    }

    private void verifyuserdata() {
        String phone = textinputlayout_phone_createuser_java.getEditText().getText().toString().trim();
        String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
        String string_fullname_phone = textinputlayout_fullname_createuser_java.getEditText().getText().toString().trim();
        String string_dob_phone = textinputlayout_dob_createuser_java.getEditText().getText().toString().trim();
        String string_phonenumber_phone = textinputlayout_phone_createuser_java.getEditText().getText().toString().trim();
        if (!(string_fullname_phone.isEmpty())) {
            if (!(string_dob_phone.isEmpty())) {
                if (!(string_phonenumber_phone.isEmpty())) {
                    if (!(string_dob_phone.length() < 10)) {
                        if (!(string_dob_phone.length() < 10)) {
                            progressDialog.show();
                            progressDialog.setContentView(R.layout.progress_dialog);
                            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            String phonenumber = "+" + code + phone;
                            sendverificationcode(phonenumber);
                        } else {
                            progressDialog.dismiss();
                            textinputlayout_phone_createuser_java.setError("Please Enter A Valid Phone Number");
                        }
                    } else {
                        progressDialog.dismiss();
                        textinputlayout_dob_createuser_java.setError("Please Enter A Valid Date Of Birth");
                    }
                } else {
                    progressDialog.dismiss();
                    textinputlayout_phone_createuser_java.setError("Fields Cannot Be Empty");
                }
            } else {
                progressDialog.dismiss();
                textinputlayout_dob_createuser_java.setError("Fields Cannot Be Empty");
            }
        } else {
            progressDialog.dismiss();
            textinputlayout_fullname_createuser_java.setError("Fields Cannot Be Empty");
        }
    }

    private void verifycode(String Code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, Code);
        signinwithcredentials(credential);
    }

    private void signinwithcredentials(PhoneAuthCredential credential) {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(create_user_phone.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String string_fullname_phone = textinputlayout_fullname_createuser_java.getEditText().getText().toString().trim();
                    String string_dob_phone = textinputlayout_dob_createuser_java.getEditText().getText().toString().trim();
                    String string_phonenumber_phone = textinputlayout_phone_createuser_java.getEditText().getText().toString().trim();
                    Users_Phone users_phone = new Users_Phone(string_fullname_phone,string_dob_phone,string_phonenumber_phone);
                    String key = databaseReference.push().getKey();
                    databaseReference.child(key).setValue(users_phone).addOnCompleteListener(create_user_phone.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                startActivity(new Intent(create_user_phone.this,UserPage.class));
                                finish();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(create_user_phone.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(create_user_phone.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(create_user_phone.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(create_user_phone.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(create_user_phone.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendverificationcode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, callbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            progressDialog.dismiss();
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code_temp = phoneAuthCredential.getSmsCode();
            if (code_temp != null) {
                verifycode(code_temp);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressDialog.dismiss();
            Toast.makeText(create_user_phone.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void init() {
        tvuserloginjava = findViewById(R.id.textviewuserloginphonepagexml);
        textinputlayout_fullname_createuser_java = findViewById(R.id.textinputlayout_fullname_createuserphone_xml);
        textinputlayout_dob_createuser_java = findViewById(R.id.textinputlayout_dob_createuserphone_xml);
        textinputlayout_phone_createuser_java = findViewById(R.id.textinputlayout_phone_createuserphone_xml);
        textinputlayout_otp_createuser_java = findViewById(R.id.textinputlayout_otp_createuserphone_xml);
        btnsendotpphonejava = findViewById(R.id.buttonsendotpphone_successxml);
        btnverifyotpphonejava = findViewById(R.id.buttonverifyotpphone_successxml);
        progressDialog = new ProgressDialog(create_user_phone.this);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("USERS/USERS_PHONE");
    }
}
