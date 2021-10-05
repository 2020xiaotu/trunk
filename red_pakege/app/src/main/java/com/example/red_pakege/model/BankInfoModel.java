package com.example.red_pakege.model;

import com.example.red_pakege.widget.CommonModel;

import java.util.List;

//import lombok.AllArgsConstructor;
//import lombok.Data;


//@Data
//@AllArgsConstructor
public class BankInfoModel {

    String name;
    String ResId;

    public BankInfoModel() {
    }

    public BankInfoModel(String name, String resId) {
        this.name = name;
        ResId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResId() {
        return ResId;
    }

    public void setResId(String resId) {
        ResId = resId;
    }

    @Override
    public String toString() {
        return "BankInfoModel{" +
                "name='" + name + '\'' +
                ", ResId='" + ResId + '\'' +
                '}';
    }
}
