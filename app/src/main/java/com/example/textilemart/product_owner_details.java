package com.example.textilemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class product_owner_details extends AppCompatActivity {
    TextView rname1,raddress1,rstore1,rphonenumber1;
    ImageView back_btn,editprofileuser;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_owner_details);
        Toast.makeText(product_owner_details.this,"In p owner 1111",Toast.LENGTH_SHORT).show();

        rname1=findViewById(R.id.rname1);
        raddress1=findViewById(R.id.raddress1);
         back_btn=findViewById(R.id.backButton);
        rstore1=findViewById(R.id.rstore1);
        editprofileuser=findViewById(R.id.editprofileuser);
        rphonenumber1=findViewById(R.id.rphonenumber1);
        final String userID = getIntent().getStringExtra("userid");
        Toast.makeText(product_owner_details.this,userID,Toast.LENGTH_SHORT).show();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(product_owner_details.this,Homepage.class));
            }
        });
        Toast.makeText(product_owner_details.this,"In p owner 2222",Toast.LENGTH_SHORT).show();

        FirebaseDatabase.getInstance().getReference().child("users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(product_owner_details.this,"In p owner 3333",Toast.LENGTH_SHORT).show();

                String Rname1 = snapshot.child("name").getValue().toString();
                String Raddress1 = snapshot.child("address").getValue().toString();
                String Rstore1 = snapshot.child("store").getValue().toString();
                String Rphonenumber1 = snapshot.child("phone").getValue().toString();
                String url = snapshot.child("purl").getValue().toString();
//                Toast.makeText(product_owner_details.this,"In p owner 4444",Toast.LENGTH_SHORT).show();

                rname1.setText(Rname1);
                raddress1.setText(Raddress1);
                rstore1.setText(Rstore1);
                rphonenumber1.setText(Rphonenumber1);
//                Toast.makeText(product_owner_details.this,"In p owner 5555",Toast.LENGTH_SHORT).show();

                Glide.with(product_owner_details.this).load(url).into(editprofileuser);
//                Toast.makeText(product_owner_details.this,"In product Owner",Toast.LENGTH_SHORT).show();
//                Glide.with(product_owner_details.this).load(url).into(editprofileuser);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}