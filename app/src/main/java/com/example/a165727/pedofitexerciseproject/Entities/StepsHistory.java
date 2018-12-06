package com.example.a165727.pedofitexerciseproject.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "StepsHistoryTable")
public class StepsHistory {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "history_id")
    int historyID;

    @NonNull
    @ColumnInfo(name = "history_day")
    int historyDay;

    @NonNull
    @ColumnInfo(name = "history_step")
    int historyStep;

    @NonNull
    @ColumnInfo(name = "history_distance")
    int historyDistance;

    public StepsHistory(@NonNull int historyID) {
        this.historyID = historyID;
    }

    public StepsHistory(@NonNull int historyDay, @NonNull int historyStep, @NonNull int historyDistance) {
        this.historyDay = historyDay;
        this.historyStep = historyStep;
        this.historyDistance = historyDistance;
    }
    public StepsHistory(@NonNull int historyID, @NonNull int historyDay, @NonNull int historyStep, @NonNull int historyDistance) {
        this.historyID = historyID;
        this.historyDay = historyDay;
        this.historyStep = historyStep;
        this.historyDistance = historyDistance;
    }

    public StepsHistory() {
    }

    @NonNull
    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(@NonNull int historyID) {
        this.historyID = historyID;
    }

    @NonNull
    public int getHistoryDay() {
        return historyDay;
    }

    public void setHistoryDay(@NonNull int historyDay) {
        this.historyDay = historyDay;
    }

    @NonNull
    public int getHistoryStep() {
        return historyStep;
    }

    public void setHistoryStep(@NonNull int historyStep) {
        this.historyStep = historyStep;
    }

    @NonNull
    public int getHistoryDistance() {
        return historyDistance;
    }

    public void setHistoryDistance(@NonNull int historyDistance) {
        this.historyDistance = historyDistance;
    }
}
