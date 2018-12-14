package com.example.a165727.pedofitexerciseproject;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Setting extends AppCompatActivity {
    Button btn_setting_clearHistory;
    public static MyStepHistoryDB myStepHistoryDB;
    RatingBar ratingBar;
    Button btn_setting_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        myStepHistoryDB = Room.databaseBuilder(Setting.this, MyStepHistoryDB.class, "historyDB").build();

        btn_setting_clearHistory = findViewById(R.id.btn_setting_clearHistory);
        ratingBar = findViewById(R.id.rating_bar);
        btn_setting_about = findViewById(R.id.btn_setting_about);

        btn_setting_clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
            }
        });

        btn_setting_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutUsIntent = new Intent(Setting.this, AboutUS.class);
                startActivity(aboutUsIntent);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(Setting.this, " Thank You!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void clearHistory()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                myStepHistoryDB.historyDao().delete();
            }
        }).start();
    }


}
