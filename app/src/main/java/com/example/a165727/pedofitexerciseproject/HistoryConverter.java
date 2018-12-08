package com.example.a165727.pedofitexerciseproject;

import com.example.a165727.pedofitexerciseproject.Entities.StepsHistory;

public class HistoryConverter {

    public static MyStepHistoryDB myStepHistoryDB;
    public HistoryConverter(){

    }
    public static void saveHistory(final int days, final int step, final int distances){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StepsHistory stepsHistory = new StepsHistory(days,step, distances);
                myStepHistoryDB.historyDao().insertHistory(stepsHistory);
            }
        }).start()
        ;
    }
}
