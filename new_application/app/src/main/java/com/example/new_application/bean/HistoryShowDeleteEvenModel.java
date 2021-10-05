package com.example.new_application.bean;

public class HistoryShowDeleteEvenModel {
    int currentPosition;
    boolean  isShow ;

    public HistoryShowDeleteEvenModel(int currentPosition, boolean isShow) {
        this.currentPosition = currentPosition;
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
