package com.example.red_pakege.net.entity;

import androidx.annotation.NonNull;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;


public class YuebaoItemEntity extends BaseEntity {

    private String time;
    private String shouyi;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShouyi() {
        return shouyi;
    }

    public void setShouyi(String shouyi) {
        this.shouyi = shouyi;
    }

    @Override
    public String toString() {
        return "YuebaoItemEntity{" +
                "time='" + time + '\'' +
                ", shouyi='" + shouyi + '\'' +
                '}';
    }
}
