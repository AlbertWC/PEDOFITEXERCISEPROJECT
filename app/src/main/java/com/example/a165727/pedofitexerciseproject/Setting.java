package com.example.a165727.pedofitexerciseproject;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Setting extends AppCompatActivity {
    Button btn_setting_clearHistory;
    public static MyStepHistoryDB myStepHistoryDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        myStepHistoryDB = Room.databaseBuilder(Setting.this, MyStepHistoryDB.class, "historyDB").build();

        btn_setting_clearHistory = findViewById(R.id.btn_setting_clearHistory);

        btn_setting_clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
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
