package com.example.a165727.pedofitexerciseproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a165727.pedofitexerciseproject.UserProfile.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail, editTextPassword, etRegisterHeight, etRegisterWeight, etRegisterAge,etRegisterNickname;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    DatabaseReference databaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.et_registeremail);
        editTextPassword = findViewById(R.id.et_registerpassword);
        progressBar = findViewById(R.id.progressbar);
        etRegisterWeight = findViewById(R.id.et_register_weight);
        etRegisterHeight = findViewById(R.id.et_register_height);
        etRegisterAge = findViewById(R.id.et_register_age);
        etRegisterNickname = findViewById(R.id.et_register_nickname);

        databaseUser = FirebaseDatabase.getInstance().getReference("Users");

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                Intent intent = new Intent(Register.this, Main_menu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty())
        {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;

        }

        if (password.isEmpty())
        {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() <  6)
        {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);


                    String register_weight = etRegisterWeight.getText().toString();
                    String register_height = etRegisterHeight.getText().toString();
                    String register_age = etRegisterAge.getText().toString();
                    String register_nickname = etRegisterNickname.getText().toString();

                    Map newPost = new HashMap();
                    newPost.put("weight",register_weight );
                    newPost.put("height",register_height );
                    newPost.put("age",register_age );
                    newPost.put("userID",user_id);
                    newPost.put("nickname", register_nickname);

                    addUser();

                    current_user_db.setValue(newPost);



                }
                else
                {
                   if (task.getException() instanceof FirebaseAuthUserCollisionException)
                   {
                       Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
    //qwe
    private void addUser()
    {
        String register_height = etRegisterHeight.getText().toString().trim();
        String register_weight = etRegisterWeight.getText().toString().trim();
        String register_age = etRegisterAge.getText().toString().trim();
        String register_nickname = etRegisterNickname.getText().toString().trim();
        String id = databaseUser.getKey();

        User newuser = new User(id, register_height,register_weight,register_age,register_nickname);


    }


}
