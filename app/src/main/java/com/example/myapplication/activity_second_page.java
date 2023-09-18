package com.example.myapplication;


import static java.security.AccessController.getContext;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LocaleManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class activity_second_page extends AppCompatActivity {

    private Button select;
    private Button upload;
    private Bitmap bitmap;
    private ImageView imageView;
    private TextView txt;
    private StorageTask storageTask;
    private String user;

   StorageReference storageReference;
   private  DatabaseReference databaseReference;
    Uri uri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second_page);
        select=findViewById(R.id.selectImageButton);
        upload=findViewById(R.id.uploadButton);
        imageView=findViewById(R.id.imageView2);
        txt=findViewById(R.id.name);
         storageReference = FirebaseStorage.getInstance().getReference("uploads");
         databaseReference=FirebaseDatabase.getInstance().getReference("uploads");
         Intent intent=getIntent();
        String receive = intent.getStringExtra("type");




        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic();
            }

    private void pic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        launcher.launch(intent);
    }
        ActivityResultLauncher<Intent> launcher
                =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
                    if (result.getResultCode()== Activity.RESULT_OK){
                        Intent data=result.getData();
                        if (data!=null&& data.getData()!=null){
                            uri=data.getData();
                            try {
                                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (uri!=null){
                            imageView.setImageBitmap(bitmap);
                        }
                    }

            } );
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (storageTask!=null&& storageTask.isInProgress()){
                    Toast.makeText(activity_second_page.this, "uploading file.....", Toast.LENGTH_SHORT).show();
                }else{
              //  uploadimage();
                addImage(receive);
                }

            }
        });

    }



    private void uploadimage() {
        final StorageReference store= storageReference.child("images/" + uri.getLastPathSegment());
        store.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Toast.makeText(activity_second_page.this, "images uploaded successfully",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_second_page.this, "images uploaded failed",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void addImage(String text){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference db=firebaseDatabase.getReference("images");
        if (uri!=null){
            StorageReference fileReference=storageReference.child(" " + System.currentTimeMillis());
            storageTask=fileReference.putFile(uri).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(activity_second_page.this, "image uploaded successfully",
                            Toast.LENGTH_SHORT).show();
                   imageData ID=new imageData(text,
                           taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                    String uploadID=databaseReference.push().getKey();
                    databaseReference.child(uploadID).setValue(ID);
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity_second_page.this, "images uploaded failed",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });

        }else Toast.makeText(activity_second_page.this, "no file selected", Toast.LENGTH_SHORT)
                .show();

    }
}

