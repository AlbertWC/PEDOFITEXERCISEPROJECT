package com.example.a165727.pedofitexerciseproject;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;


public class History extends AppCompatActivity {

    CalendarView calendarView;
    TextView tv_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        calendarView = findViewById(R.id.cv_history);
        tv_history = findViewById(R.id.tv_history);

       calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               Toast.makeText(History.this, "Hello", Toast.LENGTH_SHORT).show();



           }
       });

    }
}
