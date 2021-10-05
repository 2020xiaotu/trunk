package com.example.new_application.bean;

public class HistoryRequestDeleteEvenModel {
    int currentPosition;

    public HistoryRequestDeleteEvenModel(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
