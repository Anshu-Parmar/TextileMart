package com.example.textilemart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class owner_own_products extends AppCompatActivity {

    RecyclerView recyclerView;
    padapter adapter;
    ImageView ownerback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_own_products);

        ownerback=findViewById(R.id.ownerback);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        recyclerView = findViewById(R.id.myrec);
        ownerback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(owner_own_products.this,owner_details.class));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(owner_own_products.this));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("product"), model.class).build();
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