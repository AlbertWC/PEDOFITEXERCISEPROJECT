package com.example.a165727.pedofitexerciseproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a165727.pedofitexerciseproject.UserProfile.User;
import com.google.firebase.auth.FirebaseAuth;
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
    ListView profilelistview;
    ArrayAdapter<String> profile_adapter;
    TextView tvProfileHeight,tvProfileWeight,tvProfileAge,tvProfileNickname;
    ArrayList<String> list = new ArrayList<>();
    List<User> appUsers;

    DatabaseReference databaseref;
    String uid;

    /*@Override
    protected void onStart() {
        super.onStart();
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot userdatasnapshot: dataSnapshot.getChildren())
                {
                    User user = userdatasnapshot.getValue(User.class);
                    appUsers.add(user);
                    Toast.makeText(getApplicationContext(), user.getHeight(), Toast.LENGTH_SHORT).show();
                    profile_adapter.add(user.getHeight() + "\n" + user.getWeight() +user.getAge());
                }
                UserList adapter = new UserList(Profile.this,appUsers);
                profilelistview.setAdapter(profile_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //initialise listview


        FBuser = mAuth.getInstance().getCurrentUser();
        uid = FBuser.getUid();
        //initialise firebase
        databaseref = FirebaseDatabase.getInstance().getReference("Users");

        appUsers = new ArrayList<>();
        profile_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        tvProfileHeight = findViewById(R.id.tv_profile_height);
        tvProfileWeight = findViewById(R.id.tv_profile_weight);
        tvProfileAge = findViewById(R.id.tv_profile_age);
        tvProfileNickname = findViewById(R.id.tv_profile_nickname);

        Log.d("data", databaseref.toString());
/*
        databaserefuser = databaseref.child("Users");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String height = dataSnapshot.child("Height").getValue(String.class);
                Log.d("Tag", height );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("LOGCAT", "" + getErrorMsg());
            }
        };
        databaserefuser.addListenerForSingleValueEvent(eventListener);

*/


        databaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String snapheight = dataSnapshot.child(uid).child("height").getValue(String.class);
                String snapweight = dataSnapshot.child(uid).child("weight").getValue(String.class);
                String snapage = dataSnapshot.child(uid).child("age").getValue(String.class);
                String snapnickname = dataSnapshot.child(uid).child("nickname").getValue(String.class);

                Log.d("checking",snapheight + "");
                tvProfileHeight.setText(snapheight);
                tvProfileAge.setText(snapage);
                tvProfileNickname.setText(snapnickname);
                tvProfileWeight.setText(snapweight);
               /* profile_adapter.add("Height :"+snapheight);*/

               /* for(DataSnapshot userdatasnapshot: dataSnapshot.getChildren())
                {

                   *//* Map<String, Object> map = (Map<String, Object>) dataSnapshot.child("Users").getValue();
                    Log.d("Tag", "Value is: " + map);*//*
                    *//*String snapheight = (String) userdatasnapshot.child("Users").getValue();*//*
//                    Log.d("height", snapheight);
                   *//* Toast.makeText(getApplicationContext(), snapheight, Toast.LENGTH_SHORT).show();*//*
                    User user = userdatasnapshot.getValue(User.class);
                    if(user != null) {
                        Log.d("User", user.getAge() + "");
                    }
                    appUsers.add(user);
                   profile_adapter.add(user.getHeight() + "\n" + user.getWeight() + "\n" +user.getAge());
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*profilelistview.setAdapter(profile_adapter);*/
     /* databaserefuser = databaserefuser.child("Users");
        databaserefuser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                list.add(value);
                profile_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/










    }
   /* private void lostListView()
    {
        profilelistview = findViewById(R.id.lv_profile_biodata);
        profile_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        profilelistview.setAdapter(profile_adapter);
    }
*/
    private void addUser()
    {

    }


}
