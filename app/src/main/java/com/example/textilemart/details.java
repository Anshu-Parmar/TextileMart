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
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class details extends AppCompatActivity {
TextInputEditText rname,raddress,rphonenumber,rstore;
ImageView dimg,backBtn;
ImageView redit1;
    Bitmap bitmap;
    private Uri filepath;
Button rcontinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        rname = findViewById(R.id.rname);
        raddress = findViewById(R.id.raddress);
        dimg = findViewById(R.id.dimg);
        redit1 = findViewById(R.id.redit1);
        rphonenumber = findViewById(R.id.rphonenumber);
        rstore = findViewById(R.id.rstore);
        rcontinue = findViewById(R.id.rcontbtn);
        backBtn = findViewById(R.id.r2_back_button);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        redit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(details.this)
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
        rcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog p = new ProgressDialog(details.this);
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
                                        Toast.makeText(details.this, "Saved!!", Toast.LENGTH_SHORT).show();
                                        HashMap<String, Object> m = new HashMap<String, Object>();
                                        m.put("name", rname.getText().toString());
                                        m.put("address", raddress.getText().toString());
                                        m.put("phone", rphonenumber.getText().toString());
                                        m.put("store", rstore.getText().toString());
                                        m.put("purl", uri.toString());
                                        m.put("userid", currentuser.toString());
                                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).setValue(m);
                                        //FirebaseDatabase.getInstance().getReference().child("riders").child(currentuser).setValue(m);
                                        startActivity(new Intent(details.this, Homepage.class));
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(details.this, "ERROR!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(details.this, Homepage.class));
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                dimg.setImageBitmap(bitmap);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}