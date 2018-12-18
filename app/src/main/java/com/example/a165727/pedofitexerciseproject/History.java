package com.example.a165727.pedofitexerciseproject;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a165727.pedofitexerciseproject.Entities.StepsHistory;
import com.google.android.gms.common.util.ProcessUtils;

import java.util.ArrayList;
import java.util.List;




public class History extends AppCompatActivity {

    public History(){
    }

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
    int userSelectDay = 0;

    int main_day, main_step, main_distance;



    public static MyStepHistoryDB myStepHistoryDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        calendarView = findViewById(R.id.cv_history);
        tv_history = findViewById(R.id.tv_history);
        tv_stepsResult = findViewById(R.id.tv_history_stepResult);
        tv_distanceResult = findViewById(R.id.tv_history_distanceResult);


        daysArray = new ArrayList<Integer>();
        daysListAdapter = new ArrayAdapter<>(History.this, android.R.layout.simple_list_item_1);


        myStepHistoryDB = Room.databaseBuilder(History.this, MyStepHistoryDB.class, "historyDB").build();


       calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
               userSelectDay = dayOfMonth;
               getHistoryDay(dayOfMonth);

           }
       });
    }

    public void saveHistory(){
        Bundle bundle1 = getIntent().getExtras();
        main_day = bundle1.getInt("dayKey");
        main_step = bundle1.getInt("stepKey");
        main_distance = bundle1.getInt("distanceKey");
        new Thread(new Runnable() {
            @Override
            public void run() {
                StepsHistory stepsHistory = new StepsHistory(main_day,main_step,main_distance);;
                myStepHistoryDB.historyDao().insertHistory(stepsHistory);
            }
        }).start();


    }
    public void getHistoryDay(final int day){
        new Thread(new Runnable() {

            @Override
            public void run() {
                List<StepsHistory> stepsHistoryList = myStepHistoryDB.historyDao().getAllHistory();
                int days ;
                for (StepsHistory stepsHistory : stepsHistoryList){
                    days = stepsHistory.getHistoryDay();
                     if(days == day) {
                        steps = stepsHistory.getHistoryStep();
                        distance = stepsHistory.getHistoryDistance();
                        Log.d("Stepsresult + dayresult","" +days + " " + steps);
                    }

                    if(day > days){
                         steps = 0;
                         distance = 0;
                        Log.d("Stepsresult + dayresult","" +days + " " + steps);
                    }
                }
                showDataInTextView();

            }
        }).start();
       // Toast.makeText(History.this, "history = "+main_day,Toast.LENGTH_LONG).show();
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
