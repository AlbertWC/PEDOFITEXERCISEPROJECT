package com.example.a165727.pedofitexerciseproject.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.a165727.pedofitexerciseproject.Entities.StepsHistory;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert
    void insertHistory(StepsHistory stepsHistory);

    @Delete
    void deleteBeverage(StepsHistory stepsHistory);

    @Query("Select * from StepsHistoryTable")
    List<StepsHistory> getAllHistory();

    @Query("DELETE FROM StepsHistoryTable ")
    void delete();

}
