package com.example.a165727.pedofitexerciseproject;

import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a165727.pedofitexerciseproject.Entities.StepsHistory;

import java.util.ArrayList;
import java.util.List;


public class History extends AppCompatActivity {

    CalendarView calendarView;
    TextView tv_history;
    TextView tv_steps;
    TextView tv_distance;
    TextView tv_stepsResult;
    TextView tv_distanceResult;



    ArrayList<Integer> daysArray;
    ArrayAdapter<Integer> daysListAdapter;
    int steps = 0;
    int distance = 0;


    public static MyStepHistoryDB myStepHistoryDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        calendarView = findViewById(R.id.cv_history);
        tv_history = findViewById(R.id.tv_history);
        tv_stepsResult = findViewById(R.id.tv_history_stepResult);
        tv_distanceResult = findViewById(R.id.tv_history_distanceResult);



/*        daysArray = new ArrayList<Integer>();
        daysListAdapter = new ArrayAdapter<>(History.this, android.R.layout.simple_list_item_1);*/

        myStepHistoryDB = Room.databaseBuilder(History.this, MyStepHistoryDB.class, "historyDB").build();

       calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               saveHistory();
           }
       });

    }

    public void saveHistory(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StepsHistory stepsHistory = new StepsHistory(4,5,6);
                myStepHistoryDB.historyDao().insertHistory(stepsHistory);
                getHistoryDay();
            }
        }).start()
        ;

    }
    public void getHistoryDay(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<StepsHistory> stepsHistoryList = myStepHistoryDB.historyDao().getAllHistory();
                int days;
                for (StepsHistory stepsHistory : stepsHistoryList){
                    days = stepsHistory.getHistoryDay();
                    if(days == 4) {
                        steps = stepsHistory.getHistoryStep();
                        distance = stepsHistory.getHistoryDistance();
                    }
                }
                showDataInTextView();
            }
        }).start();
    }

    public void showDataInTextView(){
        History.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_distanceResult.setText(Integer.toString(distance));
                tv_stepsResult.setText(Integer.toString(steps));
            }
        });

    }
}
