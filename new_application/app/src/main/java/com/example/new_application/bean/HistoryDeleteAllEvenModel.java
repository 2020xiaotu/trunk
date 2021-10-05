package com.example.new_application.bean;

public class HistoryDeleteAllEvenModel {
    int currentPosition;

    public HistoryDeleteAllEvenModel(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
