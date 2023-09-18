package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    private Button report;
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
        logout=findViewById(R.id.logout1);



        Intent intent = getIntent();
        String username = intent.getStringExtra("name");
        String mail = intent.getStringExtra("mail");
        String CT=intent.getStringExtra("pl");
        String phon=intent.getStringExtra("phoneno");
        Integer hf=intent.getIntExtra("phoneno",0);
        name.setText(username);
        email.setText(mail);
        phone.setText(phon);
        city.setText(CT);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home.this,activity_first_page.class));
            }
        });



    }


}