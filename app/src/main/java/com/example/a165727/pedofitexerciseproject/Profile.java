package com.example.a165727.pedofitexerciseproject;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView tvProfileHeight,tvProfileWeight,tvProfileAge,tvProfileEmail,tvTitle;

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference;
    DataSnapshot dataSnapshot;
    private static final String TAG = "EditProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvProfileHeight = findViewById(R.id.tv_profile_height);
        tvProfileWeight  = findViewById(R.id.tv_profile_weight);
        tvProfileAge = findViewById(R.id.tv_profile_age);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users");




    }
}
