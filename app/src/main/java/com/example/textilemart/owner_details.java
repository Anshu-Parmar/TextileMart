package com.example.textilemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;

public class owner_details extends AppCompatActivity {
    private long pressed;
    EditText editname11, editaddress11, editstore11, editphone11;
    CircularImageView profile;
    ImageView AccountSettingBackButton, changePhoto, editnameimg, editaddressimg, editstoreimg, editphoneimg;
    Button save, logout, showpro;
    String depart1, name1, email1, quali1, desig1, phone1;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);

        AccountSettingBackButton = findViewById(R.id.AccountSettingBackButton);

        profile = findViewById(R.id.profile11);
        changePhoto = findViewById(R.id.changePhotoimg);

        editname11 = findViewById(R.id.editname11);
        editnameimg = findViewById(R.id.editnameimg);


        editaddress11 = findViewById(R.id.editaddress11);
        editaddressimg = findViewById(R.id.editaddressimg);


        editstoreimg = findViewById(R.id.editstoreimg);
        editstore11 = findViewById(R.id.editstore11);

        editphone11 = findViewById(R.id.editphone11);
        editphoneimg = findViewById(R.id.editphoneimg);

        save = findViewById(R.id.save);
        showpro = findViewById(R.id.showpro);

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String sname = snapshot.child("name").getValue().toString();
                String saddress = snapshot.child("address").getValue().toString();
                String sstore = snapshot.child("store").getValue().toString();
                String sphone = snapshot.child("phone").getValue().toString();
                String surl = snapshot.child("purl").getValue().toString();

                editname11.setText(sname);
                editaddress11.setText(saddress);
                editstore11.setText(sstore);
                editphone11.setText(sphone);
                Glide.with(owner_details.this).load(surl).into(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editnameimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(owner_details.this);
                mydialog.setTitle("New Name?");

                final EditText nameInput = new EditText(owner_details.this);
                nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(nameInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name1 = nameInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("name",name1);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });

        showpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(owner_details.this,owner_own_products.class));
            }
        });

        editaddressimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(owner_details.this);
                mydialog.setTitle("New Designation?");

                final EditText desigInput = new EditText(owner_details.this);
                desigInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(desigInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        desig1 = desigInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("address",desig1);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });
        editstoreimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(owner_details.this);
                mydialog.setTitle("New Qualification?");

                final EditText qualiInput = new EditText(owner_details.this);
                qualiInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(qualiInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quali1 = qualiInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("store",quali1);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });

        editphoneimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(owner_details.this);
                mydialog.setTitle("New Phone Number?");

                final EditText phoneInput = new EditText(owner_details.this);
                phoneInput.setInputType(InputType.TYPE_CLASS_PHONE);
                mydialog.setView(phoneInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phone1 = phoneInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("contact",phone1);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(owner_details.this,owner_details.class));
            }
        });

        AccountSettingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(owner_details.this,Homepage.class));
            }
        });

        logout=findViewById(R.id.logoutbtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(owner_details.this, Firstpage.class);
                startActivity(intent);
                finish();
                Toast.makeText(owner_details.this, "Successful Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //
    @Override
    public void onBackPressed(){
        if(pressed + 2000> System.currentTimeMillis()){
            super.onBackPressed();
            Intent i = new Intent(owner_details.this, Homepage .class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(owner_details.this, "Press Back again to go Back!", Toast.LENGTH_SHORT).show();
        }
        pressed = System.currentTimeMillis();
    }

}
