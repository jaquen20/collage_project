package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;

    DatabaseReference db;
    private TextView name;
    private TextView email;
    private TextView city;
    private TextView phone;
    private FirebaseAuth auth;
private String userID;
FirebaseUser firebaseUser;
    private Button report;
    private Button contribution;
    private Button logout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseDatabase = FirebaseDatabase.getInstance();
        db=firebaseDatabase.getReference("users");
        name=findViewById(R.id.show_name);
        email=findViewById(R.id.show_mail);
        phone=findViewById(R.id.show_mobile);
        city=findViewById(R.id.show_city);
        report=findViewById(R.id.report_trash);
        contribution=findViewById(R.id.see_reports);
        logout=findViewById(R.id.logout_button);
        auth=FirebaseAuth.getInstance();



        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference db=firebaseDatabase.getReference("Users");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        userID=firebaseUser.getUid();
        db.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if (userProfile!=null){
                    Toast.makeText(home.this, "welcome", Toast.LENGTH_SHORT).show();
                    name.setText(userProfile.getName());
                    email.setText(userProfile.getEmail());
                    phone.setText(userProfile.getPhone());
                    city.setText(userProfile.getCity());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(home.this, LOGIN_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, secondPage.class);

                startActivity(intent);
            }
        });

        contribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home.this,ImageLayout.class));
            }
        });




    }


}