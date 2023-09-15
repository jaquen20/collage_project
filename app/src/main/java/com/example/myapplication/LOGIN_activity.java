package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LOGIN_activity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView sign;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        sign=findViewById(R.id.sign);
        Button login = findViewById(R.id.loginButton1);
        auth=FirebaseAuth.getInstance();
        login.setOnClickListener(view -> {
            String user=username.getText().toString();
            String pass=password.getText().toString();
            if (TextUtils.isEmpty(user)||TextUtils.isEmpty(pass)){
                Toast.makeText(LOGIN_activity.this,"empty credentials",Toast.LENGTH_SHORT).show();
            } else if (pass.length()<6) {
                Toast.makeText(LOGIN_activity.this,"password too short",Toast.LENGTH_SHORT).show();
            }else Login(user,pass);
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LOGIN_activity.this, signup_activity.class));
            }
        });

    }

    private void Login(String user, String pass) {
        auth.signInWithEmailAndPassword(user, pass)
                .addOnSuccessListener(LOGIN_activity.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                    Toast.makeText(LOGIN_activity.this, "login successful",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LOGIN_activity.this, activity_second_page.class));


//                    Toast.makeText(LOGIN_activity.this, "wrong credentials", Toast.LENGTH_SHORT).show();
//                }
            }

        });
    }
}