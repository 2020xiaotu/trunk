package com.example.red_pakege.rong_yun.model;

import com.example.red_pakege.widget.CommonModel;

public class PakegeDetailModel extends CommonModel {
/*    String sendImageUrl;
    String sendUserName;
    String pakegeAmountAndCount;
    String pakegeStatus;
    String PakegeGetDetail;*/

    String imageUrl;
    String userName;
    String amount;
    int niuji;
    String date;
    boolean isBank;

    public PakegeDetailModel(String imageUrl, String userName, String amount, int niuji, String date, boolean isBank) {
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.amount = amount;
        this.niuji = niuji;
        this.date = date;
        this.isBank = isBank;
    }

    public boolean isBank() {
        return isBank;
    }

    public void setBank(boolean bank) {
        isBank = bank;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getNiuji() {
        return niuji;
    }

    public void setNiuji(int niuji) {
        this.niuji = niuji;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
