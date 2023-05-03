package com.example.textilemart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class details2 extends AppCompatActivity {
    TextInputEditText pname,pprice,pqty;
    Button pcontinuebtn;
    ImageView pimg;

    Bitmap bitmap;
    private Uri filepath;
    ImageView pedit1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pname=findViewById(R.id.pname);
        pqty=findViewById(R.id.pqty);
        pprice=findViewById(R.id.pprice);
        pimg=findViewById(R.id.pimg);
        pedit1=findViewById(R.id.pedit1);
        pcontinuebtn=findViewById(R.id.pcontinuebtn1);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        pedit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(details2.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        pcontinuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog p = new ProgressDialog(details2.this);
                p.setTitle("Uploading...");
                p.show();
                if (filepath != null) {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference uploader = storage.getReference("Image1" + new Random().nextInt(50)).child("picture/" + UUID.randomUUID().toString());
                    uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    {
                                        p.dismiss();
                                        Toast.makeText(details2.this, "Saved!!", Toast.LENGTH_SHORT).show();

                                        HashMap<String,Object> m = new HashMap<String,Object>();
                                        m.put("ProductName",pname.getText().toString());
                                        m.put("Quantity",pqty.getText().toString());
                                        m.put("Price",pprice.getText().toString());
                                        m.put("ppurl", uri.toString());
                                        m.put("userid",currentuser.toString());
                                        Toast.makeText(details2.this, "data here", Toast.LENGTH_SHORT).show();
                                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("product").child(pname.getText().toString()).setValue(m);
                                        FirebaseDatabase.getInstance().getReference().child("product").child(currentuser).child(pname.getText().toString()).setValue(m);
                                        FirebaseDatabase.getInstance().getReference().child("allproduct").child(pname.getText().toString()).setValue(m);
                                        Toast.makeText(details2.this, "now intent", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(details2.this, "ERROR!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }




//                FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Toast.makeText(details2.this, "nnnn", Toast.LENGTH_SHORT).show();
//                    for (DataSnapshot s : snapshot.getChildren()) {
//
//                    String userid = s.child("userid").getValue().toString();
//                    Toast.makeText(details2.this,userid,Toast.LENGTH_SHORT).show();
//                    if(!userid.equals(currentuser))
//                    {
//                        Toast.makeText(details2.this, "in IF", Toast.LENGTH_SHORT).show();
//
//                        FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("product").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                Toast.makeText(details2.this, "up for", Toast.LENGTH_SHORT).show();
//
//                                for(DataSnapshot s2:snapshot.getChildren())
//                                {
//                                    Toast.makeText(details2.this, "down for", Toast.LENGTH_SHORT).show();
//
//                                    String productname = s2.child("ProductName").getValue().toString();
//                                    String price = s2.child("Price").getValue().toString();
//                                    String quantity = s2.child("Quantity").getValue().toString();
//                                    Toast.makeText(details2.this, "nnnn111111111", Toast.LENGTH_SHORT).show();
//
//                                    HashMap<String,Object> m = new HashMap<String, Object>();;
//                                    m.put("ProductName",productname);
//                                    m.put("Price",price);
//                                    m.put("Quantity",quantity);
//                                    m.put("userid",userid);
//                                    Toast.makeText(details2.this, "mput", Toast.LENGTH_SHORT).show();
//
//                                    FirebaseDatabase.getInstance().getReference().child("product").child(currentuser).child(productname).updateChildren(m);
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//
//
//                }
//            }
//            @Override
//            public void onCancelled (@NonNull DatabaseError error){
//
//            }
//        });
                startActivity(new Intent(details2.this,Homepage.class));


            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                pimg.setImageBitmap(bitmap);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}