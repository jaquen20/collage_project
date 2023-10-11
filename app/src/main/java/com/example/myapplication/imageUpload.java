package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class imageUpload {

    private static final String STORAGE_PATH = "user_images/";

    public static void uploadImageAndStoreURL(Uri imageUri, String imageName, String userId) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference userImageReference = storageReference.child(STORAGE_PATH + userId + "/" + imageName);

        UploadTask uploadTask = userImageReference.putFile(imageUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            userImageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                //Toast.makeText(,"uploaded....", Toast.LENGTH_SHORT).show();
                storeImageUrlInDatabase(userId, imageUrl);
            });
        });
    }

    private static void storeImageUrlInDatabase(String userId, String imageUrl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference userReference = databaseReference.child(userId);
        DatabaseReference imageUrlsReference = userReference.child("imageUrls").push();
        imageUrlsReference.setValue(imageUrl);
    }
    public static void showImage(Context context, String userId, ImageView imageView) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        // Retrieve the user's image URLs
        userReference.child("imageUrls").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot imageUrlSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = imageUrlSnapshot.getValue(String.class);

                    // Load and display the image using Picasso
                    Picasso.get().load(imageUrl).into(imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that may occur
                // You can add error handling code here if needed
            }
        });
    }
}

