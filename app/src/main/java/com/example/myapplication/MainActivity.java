package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.login);
        Button signup = findViewById(R.id.signup);
        login.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,LOGIN_activity.class));

        });

        signup.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,signup_activity.class));

        });


    }
}