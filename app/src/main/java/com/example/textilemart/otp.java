package com.example.textilemart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity {
    TextInputEditText phone, otp;
    Button csendcodeBtn,btnverifyOTP1;
    FirebaseAuth mAuth;
    String verificationID;
    ProgressBar bar;
    PinView pin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);
        phone = findViewById(R.id.cphone1);
        otp = findViewById(R.id.otp1);
        csendcodeBtn = findViewById(R.id.csendcodeBtn);
        btnverifyOTP1 =findViewById(R.id.btnverifyOTP1);
        mAuth = FirebaseAuth.getInstance();
        bar = findViewById(R.id.bar);
        csendcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(phone.getText().toString()))
                {
                    Toast.makeText(otp.this, "Enter Valid Phone No.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String number = phone.getText().toString();
                    Toast.makeText(com.example.textilemart.otp.this, "No entered.", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.VISIBLE);
                    sendverificationcode(number);
                }
            }
        });
        btnverifyOTP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(otp.getText().toString()))
                {
                    Toast.makeText(otp.this, "Wrong OTP Entered", Toast.LENGTH_SHORT).show();
                }
                else {
                    bar.setVisibility(View.VISIBLE);
                    verifycode(otp.getText().toString());
                }
            }
        });
    }

    private void sendverificationcode(String phoneNumber)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneNumber)  // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {
            final String code = credential.getSmsCode();
            if(code!=null)
            {
                Toast.makeText(otp.this, "Verification ", Toast.LENGTH_SHORT).show();
                verifycode(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(otp.this, "Verification Failed", Toast.LENGTH_SHORT).show();
            bar.setVisibility(View.INVISIBLE);
        }
        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(s, token);
            verificationID = s;
            Toast.makeText(otp.this, "Code sent", Toast.LENGTH_SHORT).show();
            btnverifyOTP1.setEnabled(true);
            bar.setVisibility(View.INVISIBLE);
        }


    };
    private void verifycode(String Code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,Code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(otp.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(otp.this, details.class));
                        }

                    }
                });}

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            startActivity(new Intent(otp.this, details.class));
            finish();
        }}}