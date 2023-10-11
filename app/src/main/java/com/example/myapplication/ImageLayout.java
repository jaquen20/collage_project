package com.example.myapplication;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



import java.util.ArrayList;
import java.util.List;

public class ImageLayout extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private DatabaseReference databaseReference;

    private List<garbageClass> garbageclassList;

    private List<String> imageUrls;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_layout);

        //auth = FirebaseAuth.getInstance();


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageUrls = new ArrayList<>();
        adapter = new Adapter(this, imageUrls);
        recyclerView.setAdapter(adapter);


        garbageclassList = new ArrayList<>();

       // adapter = new Adapter(this, garbageclassList);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            DatabaseReference databaseReference =
                    FirebaseDatabase.getInstance().
                            getReference("userImages").child(userId);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageUrls.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String imageUrl = postSnapshot.child("imageURL").getValue(String.class);
                  //  String imageUrl = postSnapshot.getValue(String.class);
                   // garbageClass gc = postSnapshot.getValue(garbageClass.class);
                    if (imageUrl != null) {
                        imageUrls.add(imageUrl);
                    }
//                    if (gc != null) {
//                        garbageclassList.add(gc);
//                    } else Toast.makeText(ImageLayout.this, "gc null", Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
//                adapter = new Adapter(ImageLayout.this, garbageclassList);
//                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ImageLayout.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }
        else Toast.makeText(this, "user not assigned", Toast.LENGTH_SHORT).show();

//        userReference.child(auth.getCurrentUser().getUid()).child("imageUrls")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot imageUrlSnapshot : dataSnapshot.getChildren()) {
//                    garbageclass gc=imageUrlSnapshot.getValue(garbageclass.class);
//                    garbageclassList.add(gc);
//
//                    String imageUrl = imageUrlSnapshot.getValue(String.class);
//                    //Glide.with(getApplicationContext()).load(imageUrl).into(recyclerView);
//                    //adapter=new imageData(ImageLayout.this,garbageclassList);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//                Log.e("FirebaseDatabase", "Error fetching image URLs: " + databaseError.getMessage());
//            }
//        });
    }
}