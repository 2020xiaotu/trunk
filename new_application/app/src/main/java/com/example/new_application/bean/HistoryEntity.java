package com.example.new_application.bean;

import java.util.ArrayList;

public class HistoryEntity {
    boolean isCheck =false;
    boolean isShow = false;//是否显示左侧删除

    ArrayList<ClassificationDetailsChildEntity> classificationDetailsChildEntityArrayList;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public ArrayList<ClassificationDetailsChildEntity> getClassificationDetailsChildEntityArrayList() {
        return classificationDetailsChildEntityArrayList;
    }

    public void setClassificationDetailsChildEntityArrayList(ArrayList<ClassificationDetailsChildEntity> classificationDetailsChildEntityArrayList) {
        this.classificationDetailsChildEntityArrayList = classificationDetailsChildEntityArrayList;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
