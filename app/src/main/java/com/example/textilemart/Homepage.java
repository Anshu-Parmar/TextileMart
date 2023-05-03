package com.example.textilemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Homepage extends AppCompatActivity {

    RecyclerView recyclerView;
    padapter adapter;
    ImageView add,profilev;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView = findViewById(R.id.recyclerView);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        add = findViewById(R.id.add);
        profilev=findViewById(R.id.profilev);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this,details2.class));
            }
        });
profilev.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(Homepage.this,owner_details.class));
    }
});
//        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(Homepage.this, "nnnn", Toast.LENGTH_SHORT).show();
//                for (DataSnapshot s : snapshot.getChildren()) {
//                    String userid = s.child("userid").getValue().toString();
//                    FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("product").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            String productname = snapshot.child("ProductName").getValue().toString();
//                            String price = snapshot.child("Price").getValue().toString();
//                            String quantity = snapshot.child("Quantity").getValue().toString();
//                            HashMap<String,Object> h = new HashMap<String, Object>();;
//                            h.put("ProductName",productname);
//                            h.put("Price",price);
//                            h.put("Quantity",quantity);
//                            FirebaseDatabase.getInstance().getReference().child("product").child(currentuser).updateChildren(h);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//            @Override
//            public void onCancelled (@NonNull DatabaseError error){
//
//            }
//        });
        recyclerView.setLayoutManager(new LinearLayoutManager(Homepage.this));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("allproduct"), model.class).build();
        adapter = new padapter(options, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}