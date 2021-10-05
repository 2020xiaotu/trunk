package com.example.red_pakege.model;

import com.example.red_pakege.activity.mine.RecordActivity;
import com.example.red_pakege.widget.CommonModel;

public class RecordModel extends CommonModel {

    private RecordActivity.TYPE mtype;
    private String type;
    private String time;
    private String money;
    private String status;


    public RecordActivity.TYPE getMtype() {
        return mtype;
    }

    public void setMtype(RecordActivity.TYPE mtype) {
        this.mtype = mtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
