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

               // registerUser();
                register();

            } else {
                Toast.makeText(signup_activity.this, "turn on the internet", Toast.LENGTH_SHORT).show();
            }

//            String user=username2.getText().toString();
//            String mail=email2.getText().toString();
//            String pass=password2.getText().toString();
//           if (TextUtils.isEmpty(mail)||TextUtils.isEmpty(pass)){
//                Toast.makeText(signup_activity.this, "empty credentials",
//                        Toast.LENGTH_SHORT).show();
//               return;
//            }
//            auth.createUserWithEmailAndPassword(mail, pass)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d(TAG, "createUserWithEmail:success");
//                                Toast.makeText(signup_activity.this, "Authentication successfully.",
//                                        Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(signup_activity.this, activity_first_page.class));
//
//
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                Toast.makeText(signup_activity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
//
//        });
        });
    }

//    private void addDatatoFirebase(EditText username2, EditText email2, EditText password2) {
//        user.setName(String.valueOf(username2));
//        user.setEmail(String.valueOf(email2));
//        user.setPassword(String.valueOf(password2));
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // inside the method of on Data change we are setting
//                // our object class to our database reference.
//                // data base reference will sends data to firebase.
//                databaseReference.setValue(user);
//
//                // after adding this data we are showing toast message.
//                Toast.makeText(signup_activity.this, "data added", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // if the data is not added or it is cancelled then
//                // we are displaying a failure toast message.
//                Toast.makeText(signup_activity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser() != null) {
            //handle the already login user
        }
    }
    private void registerUser() {
//        final String name = username2.getText().toString().trim();
//        final String email = email2.getText().toString().trim();
//        String password = password2.getText().toString().trim();
//        final String phone = editTextPhone.getText().toString().trim();
//
//        if (name.isEmpty()) {
//            username2.setError(getString(R.string.input_error_name));
//            username2.requestFocus();
//            return;
//        }
//
//        if (email.isEmpty()) {
//            email2.setError(getString(R.string.input_error_email));
//            email2.requestFocus();
//            return;
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            email2.setError(getString(R.string.input_error_email_invalid));
//            email2.requestFocus();
//            return;
//        }
//
//        if (password.isEmpty()) {
//            password2.setError(getString(R.string.input_error_password));
//            password2.requestFocus();
//            return;
//        }
//
//        if (password.length() < 6) {
//            password2.setError(getString(R.string.input_error_password_length));
//            password2.requestFocus();
//            return;
//        }
//
//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        if (task.isSuccessful()) {
//
//                            User user = new User(
//                                    name,
//                                    email,
//                                    password
//                            );
//
//                            FirebaseDatabase.getInstance().getReference("Users")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                            if (task.isSuccessful()) {
//                                                Toast.makeText(signup_activity.this,
//                                                        getString(R.string.registration_success),
//                                                        Toast.LENGTH_LONG).show();
//                                            } else {
//                                                //display a failure message
//                                            }
//                                        }
//                                    });
//
//                        } else {
//                            Toast.makeText(signup_activity.this, task.getException()
//                                    .getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//
//    }

            String user=username2.getText().toString();
            String mail=email2.getText().toString();
            String pass=password2.getText().toString();
            Integer ph= Integer.valueOf(phone.getText().toString());
            String place= city.getText().toString();
           if (TextUtils.isEmpty(mail)||TextUtils.isEmpty(pass) || TextUtils.isEmpty(user)
                   ||TextUtils.isEmpty(place)){
                Toast.makeText(signup_activity.this, "empty credentials",
                        Toast.LENGTH_SHORT).show();
               return;
            }
            auth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
//                                addDatatoFirebase(username2, email2, password2);
                                Toast.makeText(signup_activity.this, "Authentication successfully.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(signup_activity.this, home.class));
                                finish();


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(signup_activity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

            }




    public void register(){
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");
                String user=username2.getText().toString();
                String mail=email2.getText().toString();
                String pass=password2.getText().toString();
                Integer ph= Integer.valueOf(phone.getText().toString());
                String place= city.getText().toString();
                User user1=new User(user,mail,pass,place,ph);
                databaseReference.child(user).setValue(user1);
                Toast.makeText(signup_activity.this, "registration successful",
                        Toast.LENGTH_SHORT).show();
                getData(user,mail,ph,place);

            }
    public void getData(String name,String mail,Integer ph,String place){
        Intent intent=new Intent(getApplicationContext(), home.class);
        intent.putExtra("name",name);
        intent.putExtra("mail",mail);
        intent.putExtra("phoneno",ph);
        intent.putExtra("pl",place);
        startActivity(intent);
        finish();

    }
    private boolean isValidNo(String N){
        return Patterns.PHONE.matcher(N).matches();


    }
}
