package com.example.chatx.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatx.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText phone_number = findViewById(R.id.phone_number);
        CountryCodePicker ccp = findViewById(R.id.ccp);
        Button continuebtn = findViewById(R.id.continue_button);
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ccp.registerCarrierNumberEditText(phone_number);
                if(phone_number.length() != 10){
                    Toast.makeText(login.this, "Enter a valid phone number",Toast.LENGTH_SHORT).show();
                }else{
                    String PHONE_NUMBER = ccp.getFullNumberWithPlus();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    sendOTP(mAuth, PHONE_NUMBER);


//                    Intent intent = new Intent(login.this, OTP_verification.class);
//                    intent.putExtra("PHONE_NUMBER",full_phone_number);
//                    startActivity(intent);
                }
            }
        });
    }
    private void sendOTP(FirebaseAuth mAuth, String PHONE_NUMBER) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(PHONE_NUMBER)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(login.this, "otp instantly verified",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.d("verification", e.getMessage());
                                Toast.makeText(login.this, "verification failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Toast.makeText(login.this, "code is sent",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, OTP_verification.class);
                                intent.putExtra("OTP",s);
                                startActivity(intent);
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}