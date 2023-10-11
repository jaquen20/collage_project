package com.example.myapplication;


import static java.security.AccessController.getContext;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LocaleManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class activity_second_page extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private Button select;
    private Button upload;
    private Bitmap bitmap;
    private ImageView imageView;
    private TextView locationTxt;
    private StorageTask storageTask;
    private FirebaseAuth auth;
    private String user;



    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Uri uri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_second_page);
        select = findViewById(R.id.selectImageButton);
        upload = findViewById(R.id.uploadButton);
        imageView = findViewById(R.id.imageView2);
        locationTxt = findViewById(R.id.locationView);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");



        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            uri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (uri != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }

                });


        select.setOnClickListener(view -> {
//            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                // Permission is not granted, request it
//                ActivityCompat.requestPermissions(this, new String[]{
//                        Manifest.permission.CAMERA}, 100);
//            }
//            Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
//           //startActivityForResult(intent, 100);
//            launcher.launch(intent);

//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            //intent.setType("image/*");
//           // intent.setAction(Intent.ACTION_GET_CONTENT);
//            launcher.launch(intent);
            captureImage();


        });

        upload.setOnClickListener(view -> {
            if (storageTask != null && storageTask.isInProgress()) {
                Toast.makeText(activity_second_page.this, "uploading file.....", Toast.LENGTH_SHORT).show();
            } else {
                upload_Image();
                //imageUpload.uploadImageAndStoreURL(uri,"plastic",auth.getCurrentUser().getUid());
            }

        });

    }

    public void upload_Image() {
        if (uri != null) {
           // auth = FirebaseAuth.getInstance();

            //String userId=auth.getCurrentUser().getUid();
          //  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
         //   DatabaseReference userReference = databaseReference.child(userId);
          //  StorageReference storageReference = FirebaseStorage.getInstance().getReference();
           // StorageReference userImageReference = storageReference.child(STORAGE_PATH + userId + "/" + "imageName");

            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".jpg");
            storageTask = fileReference.putFile(uri).
                    addOnSuccessListener(taskSnapshot -> {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                .addOnSuccessListener(url -> {
                                    imageData ID = new imageData(url.toString());
                                    //Log.d("HOMEACTIVITY", ID.toString());
                                   // String uploadID = databaseReference.push().getKey();
                                   // Log.d("HOMEACTIVITY", uploadID.toString());
//                                    DatabaseReference imageUrlsReference = userReference.child("imageUrls").push();
//                                    imageUrlsReference.setValue(ID).addOnSuccessListener(success->
//                                            Toast.makeText(activity_second_page.this,
//                                                    "image uploaded successfully", Toast.LENGTH_SHORT).show());
//
//                                            });
                                    databaseReference.setValue(ID)
                                    //child(auth.getCurrentUser().getUid()).
                                            .addOnSuccessListener(success ->

                                                    Toast.makeText(activity_second_page.this,
                                                            "image uploaded successfully", Toast.LENGTH_SHORT).show());
                                   // imageView.setImageDrawable(null);

                                });

                    }).addOnFailureListener(e -> Toast.makeText(activity_second_page.this, "images uploaded failed",
                            Toast.LENGTH_SHORT).show());
        } else Toast.makeText(activity_second_page.this, "no file selected", Toast.LENGTH_SHORT)
                .show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(activity_second_page.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity_second_page.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the missing permissions.
            // You can show a toast message to inform the user.
            Toast.makeText(activity_second_page.this, "Location permission is required to show the map.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity_second_page.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_CODE);
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity_second_page.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            // Create a LatLng object from the location
                            // LatLng userLatLng = new LatLng(latitude, longitude);

                            // Create a Geocoder object to convert location to address
                            Geocoder geocoder = new Geocoder(activity_second_page.this, Locale.getDefault());

                            try {
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                if (!addresses.isEmpty()) {
                                    Address address = addresses.get(0);
                                    String userAddress = address.getAddressLine(0);

                                    // Display the user's address in the TextView
                                    locationTxt.setText(userAddress);
                                    locationTxt.setVisibility(View.VISIBLE);

                                } else {
                                    // Handle the case where no address is found.
                                    locationTxt.setText("Address not found");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(activity_second_page.this, "Geocoding error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void captureImage() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA}, 100);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {

           // Uri uri;
            uri = data.getData();
            Bitmap data1 = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(data1);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            getLocation();
           // upload_Image();

        }

    }


//    public static void uploadImageAndStoreURL(Uri imageUri,String userId,imageName) {
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//
//        StorageReference userImageReference = storageReference.child(STORAGE_PATH + userId + "/" + imageName);
//
//        UploadTask uploadTask = userImageReference.putFile(imageUri);
//
//        uploadTask.addOnSuccessListener(taskSnapshot -> {
//            userImageReference.getDownloadUrl().addOnSuccessListener(uri -> {
//                String imageUrl = uri.toString();
//                storeImageUrlInDatabase(userId, imageUrl);
//            });
//        });
//    }
//
//    private static void storeImageUrlInDatabase(String userId, String imageUrl) {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
//        DatabaseReference userReference = databaseReference.child(userId);
//        DatabaseReference imageUrlsReference = userReference.child("imageUrls").push();
//        imageUrlsReference.setValue(imageUrl);
//    }
}

