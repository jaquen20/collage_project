package com.example.myapplication;

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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class secondPage extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private Button select;
    private Button upload;
    private Button openOnMapButton;
    private TextView tvSelectType,locationTextview;
    private RadioGroup radioGroup;
    private Button submitButton;
    private ImageView imageView;
    private TextView addressTextView; // Add this line
    private TextView addTextView;
    private Uri uri;
    private  Bitmap bitmap;

    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        select = findViewById(R.id.selectImageButton);
       upload = findViewById(R.id.uploadButton);
        imageView = findViewById(R.id.imageView1);
        locationTextview = findViewById(R.id.locationView);
       // openOnMapButton = findViewById(R.id.openOnMapButton);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
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
            if (data != null) {

                if (data.getData() != null) {
                    uri = data.getData();
                    imageView.setImageURI(uri);
                } else {

                     bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    } else {

                    }
                }
                getLocation();
            }
        }
    }



        //select.setVisibility(View.GONE);

        //upload.setVisibility(View.VISIBLE);
        // Show the Select Type text and Radio Group
        //tvSelectType.setVisibility(View.VISIBLE);
       // radioGroup.setVisibility(View.VISIBLE);

        // Show the Submit button
        //submitButton.setVisibility(View.VISIBLE);
       // addressTextView.setVisibility(View.VISIBLE);
        // Show the Address TextView and Add TextView
        //addTextView.setVisibility(View.VISIBLE);

    private void uploadImage() {
        if (uri != null) {
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("userImages");
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final StorageReference store = storageReference.child("images/" + uri.getLastPathSegment());
            store.putFile(uri).
            addOnSuccessListener(taskSnapshot -> {

                            taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                    .addOnSuccessListener(url -> {
                                        imageData ID = new imageData(url.toString());


                                        databaseReference.child(userId).setValue(ID);
                                        startActivity(new Intent(secondPage.this, home.class));

                                        Toast.makeText(secondPage.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(secondPage.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                    });
        }

        else if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageData = baos.toByteArray();
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("userImages");
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String imageId = databaseReference.push().getKey();


            StorageReference store = storageReference.child("images/" + System.currentTimeMillis());


            UploadTask uploadTask = store.putBytes(imageData);

            uploadTask.addOnSuccessListener(taskSnapshot -> {

                taskSnapshot.getMetadata().getReference().getDownloadUrl()
                        .addOnSuccessListener(url -> {
                            imageData ID = new imageData(url.toString());


                            databaseReference.child(userId).child(imageId).setValue(ID);
                            startActivity(new Intent(secondPage.this, home.class));

                            Toast.makeText(secondPage.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(secondPage.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                            }
                        });

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(secondPage.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                    // Handle failure, if needed
                }
            });
        }
    else {
            Toast.makeText(secondPage.this, "Please capture an image first", Toast.LENGTH_SHORT).show();
        }
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(secondPage.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(secondPage.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the missing permissions.
            // You can show a toast message to inform the user.
            Toast.makeText(secondPage.this, "Location permission is required to show the map.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(secondPage.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_CODE);
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(secondPage.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            // Create a LatLng object from the location
                            // LatLng userLatLng = new LatLng(latitude, longitude);

                            // Create a Geocoder object to convert location to address
                            Geocoder geocoder = new Geocoder(secondPage.this, Locale.getDefault());

                            try {
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                if (!addresses.isEmpty()) {
                                    Address address = addresses.get(0);
                                    String userAddress = address.getAddressLine(0);

                                    // Display the user's address in the TextView
                                    locationTextview.setText(userAddress);
                                    locationTextview.setVisibility(View.VISIBLE);

                                } else {
                                    // Handle the case where no address is found.
                                    locationTextview.setText("Address not found");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(secondPage.this, "Geocoding error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


//    private void openLocationOnMap() {
//        // Get the address from the TextView
//        String address = locationTextview.getText().toString();
//
//        // Create a Uri for opening the location on a map app
//        Uri locationUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
//
//        // Create an Intent to view the location on a map app
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
//        mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package for Google Maps
//
//        // Check if there is an app to handle the Intent
//        if (mapIntent.resolveActivity(getPackageManager()) != null) {
//            // Start the map activity
//            startActivity(mapIntent);
//        } else {
//            // Handle the case where no map app is installed
//            Toast.makeText(secondPage.this, "No map app is installed.", Toast.LENGTH_SHORT).show();
//        }
//    }

}

