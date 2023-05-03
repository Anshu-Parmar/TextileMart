package com.example.textilemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ImageView d_back_button;
    Button rnextBtn;
    TextView alreadyhaveacc;
    TextInputEditText remail,rpwd,crpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        remail=findViewById(R.id.remail);
        rpwd= findViewById(R.id.rpwd);
        crpwd= findViewById(R.id.crpwd);
        alreadyhaveacc=findViewById(R.id.alreadyhaveacc);
        d_back_button=findViewById(R.id.d_back_button);
        rnextBtn=(Button)findViewById(R.id.rnextBtn);
        d_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        alreadyhaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,login.class));
            }
        });
        rnextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = remail.getText().toString();
                String Password = rpwd.getText().toString();
                if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password))
                {
                    Toast.makeText(MainActivity.this , "Enter Values!!" , Toast.LENGTH_LONG).show();
                }
                else{
//                    Toast.makeText(MainActivity.this,"HELLLLLL",Toast.LENGTH_SHORT).show();
                    regist(Email,Password);
                }
            }
        });
    }
    private void regist(String Email, String Password) {
        auth.createUserWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(MainActivity.this, "Verify your Email ID!!!", Toast.LENGTH_SHORT).show();

                        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this , "Registered Successfully!!" , Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this,login.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//            private void regist(String Email, String Password) {
//                auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful())
//                        {
//                            Toast.makeText(MainActivity.this , "Registered Successfully!!" , Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(MainActivity.this,otp.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                        else{
//                            Toast.makeText(MainActivity.this , "Registration Failed!!" , Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        });

    }
}
