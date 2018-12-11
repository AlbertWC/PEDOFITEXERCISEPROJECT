package com.example.a165727.pedofitexerciseproject;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a165727.pedofitexerciseproject.UserProfile.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser FBuser;
    private ListView profilelistview;
    private ArrayAdapter<String> profile_adapter;

    List<User> appUsers;

    DatabaseReference databaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        profilelistview = findViewById(R.id.lv_profile_biodata);
        databaseUser = FirebaseDatabase.getInstance().getReference("Users");
        appUsers = new ArrayList<>();
        lostListView();
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot Userdatasnapshot: dataSnapshot.getChildren())
                {
                    User user = Userdatasnapshot.getValue(User.class);
                    appUsers.add(user);
                    profile_adapter.add(user.getHeight() + "\n" + user.getWeight() +user.getAge());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });











    }
    private void lostListView()
    {
        profilelistview = findViewById(R.id.lv_profile_biodata);
        profile_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        profilelistview.setAdapter(profile_adapter);
    }



}
