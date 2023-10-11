package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LOGIN_activity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView sign;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private  String userID;

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
                return;
            }  if (pass.length()<6) {
                Toast.makeText(LOGIN_activity.this,"password too short",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()){
                username.setError("please enter valid email");
                return;
            }

                ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    Login(user, pass);
                    //log(user,pass);
                }else{
                    Toast.makeText(this, "turn on the internet you twat", Toast.LENGTH_SHORT).show();
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


                    log(user,pass);

            }

        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LOGIN_activity.this, "error occured", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void log(String user,String pass){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference db=firebaseDatabase.getReference("Users");
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        userID=firebaseUser.getUid();

        db.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                if (userProfile!=null){
                    Toast.makeText(LOGIN_activity.this, "welcome", Toast.LENGTH_SHORT).show();
                    String N=snapshot.child(userID).child("name").getValue(String.class);
                    String M=snapshot.child(userID).child("email").getValue(String.class);
                    String C=snapshot.child(userID).child("city").getValue(String.class);
                    String P=snapshot.child(userID).child("phone").getValue(String.class);
                    getData(N,M,C,P);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Query query=db.orderByChild("userId").equalTo(auth.getCurrentUser().getUid());
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    String val=snapshot.child(auth.getCurrentUser().getUid()).child("password").getValue(String.class);
//                    if(val.equals(pass)){
//
//                        Toast.makeText(LOGIN_activity.this, "welcome", Toast.LENGTH_SHORT).show();
//                        String N=snapshot.child(auth.getCurrentUser().getUid()).child("name").getValue(String.class);
//                        String M=snapshot.child(auth.getCurrentUser().getUid()).child("email").getValue(String.class);
//                        String C=snapshot.child(auth.getCurrentUser().getUid()).child("city").getValue(String.class);
//                        String P=snapshot.child(auth.getCurrentUser().getUid()).child("phone").getValue(String.class);
//                            getData(N,M,C,P);
//
//                    }
//                    else{
//                        Toast.makeText(LOGIN_activity.this, "wrong pass", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }else{
//                    Toast.makeText(LOGIN_activity.this, "username does not exists", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
    public void getData(String name,String mail,String ph,String place){
        Intent intent=new Intent(getApplicationContext(), home.class);
        intent.putExtra("name",name);
        intent.putExtra("mail",mail);
        intent.putExtra("phoneno",ph);
        intent.putExtra("pl",place);
        startActivity(intent);
        finish();

    }

}