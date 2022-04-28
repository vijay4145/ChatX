package com.example.chatx.account_setup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.chatx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTP_verification extends AppCompatActivity {
    private String OTP;
    FirebaseAuth mAuth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Verifying OTP....");
        setContentView(R.layout.activity_otp_verification);
        Intent intent = getIntent();
        OTP = intent.getStringExtra("OTP");
        mAuth = FirebaseAuth.getInstance();
        EditText otp_entered_by_user = findViewById(R.id.otp_number_editBox);
        Button validate_otp_button = findViewById(R.id.continue_in_otp);
        validate_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp_by_user = otp_entered_by_user.getText().toString();
                if(otp_by_user.length() != 6){
                    Toast.makeText(OTP_verification.this, "NOT A VALID OTP", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP, otp_by_user);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTP_verification.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(OTP_verification.this, "signin completed",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(OTP_verification.this, set_user_profile.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(OTP_verification.this, "enter a valid otp",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}