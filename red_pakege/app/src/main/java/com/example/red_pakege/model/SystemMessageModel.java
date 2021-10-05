package com.example.red_pakege.model;

import com.example.red_pakege.widget.CommonModel;

public class SystemMessageModel extends CommonModel {
    String title;
    String content;
    String time;
    int status=0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public SystemMessageModel(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }
}
