package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class signup_activity extends AppCompatActivity {
    private EditText username2;
    private EditText email2;
    private EditText password2;
    private EditText phone;
    private EditText city;
    private Button register;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_actvity);
        username2 = findViewById(R.id.name);
        email2 = findViewById(R.id.email1);
        password2 = findViewById(R.id.password1);
        city=findViewById(R.id.city);
        phone=findViewById(R.id.mobile);


        register = findViewById(R.id.signButton);


        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(View -> {
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (isConnected) {

                registerUser();


            } else {
                Toast.makeText(signup_activity.this, "turn on the internet", Toast.LENGTH_SHORT).show();
            }

        });
    }



    private void registerUser() {


        String user = username2.getText().toString();
        String mail = email2.getText().toString();
        String pass = password2.getText().toString();
        String ph = phone.getText().toString();
        String place = city.getText().toString();
        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(user)
                || TextUtils.isEmpty(place)) {
            Toast.makeText(signup_activity.this, "empty credentials",
                    Toast.LENGTH_SHORT).show();
            return;
        }  if (phone.getText().toString().length()<10|| phone.getText().toString().length()>10) {
            Toast.makeText(signup_activity.this, "phone no is wrong", Toast.LENGTH_SHORT).show();
            return;

        }

            auth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                register(user,mail,pass,ph,place);
                                Log.d(TAG, "createUserWithEmail:success");
//
                                Toast.makeText(signup_activity.this, "Authentication successfully.",
                                        Toast.LENGTH_SHORT).show();
                               // startActivity(new Intent(signup_activity.this, home.class));
                                //finish();


                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(signup_activity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }





    public void register(String user,String mail,String pass,String ph,String place){
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");
        String uid=auth.getCurrentUser().getUid();

//                 user=username2.getText().toString();
//                 mail=email2.getText().toString();
//                 pass=password2.getText().toString();
//                 ph= phone.getText().toString();
//                 place= city.getText().toString();
                User user1=new User(uid,user,mail,pass,place,ph);
                databaseReference.child(auth.getCurrentUser().getUid()).setValue(user1);
                Toast.makeText(signup_activity.this, "registration successful",
                        Toast.LENGTH_SHORT).show();
                getData(user,mail,ph,place);

            }
    public void getData(String name,String mail,String ph,String place){
        Intent intent=new Intent(getApplicationContext(), home.class);
        intent.putExtra("id",auth.getCurrentUser().getUid());
        intent.putExtra("name",name);
        intent.putExtra("mail",mail);
        intent.putExtra("phoneno",ph);
        intent.putExtra("pl",place);
        startActivity(intent);
        finish();

    }

}
