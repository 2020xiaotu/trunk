package com.example.new_application.bean;

import java.util.ArrayList;

public class FollowEntity {
    String content;

    public FollowEntity(String content) {
        this.content = content;
    }

    ArrayList<ClassificationDetailsChildEntity> classificationDetailsChildEntityArrayList;

    public ArrayList<ClassificationDetailsChildEntity> getClassificationDetailsChildEntityArrayList() {
        return classificationDetailsChildEntityArrayList;
    }

    public void setClassificationDetailsChildEntityArrayList(ArrayList<ClassificationDetailsChildEntity> classificationDetailsChildEntityArrayList) {
        this.classificationDetailsChildEntityArrayList = classificationDetailsChildEntityArrayList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
