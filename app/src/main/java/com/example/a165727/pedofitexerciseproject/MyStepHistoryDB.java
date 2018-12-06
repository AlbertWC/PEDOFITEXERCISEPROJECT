package com.example.a165727.pedofitexerciseproject;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.a165727.pedofitexerciseproject.Dao.HistoryDao;
import com.example.a165727.pedofitexerciseproject.Entities.StepsHistory;

@Database(entities = {StepsHistory.class}, version = 1)
public abstract class MyStepHistoryDB extends RoomDatabase{

    public abstract HistoryDao historyDao();
}
