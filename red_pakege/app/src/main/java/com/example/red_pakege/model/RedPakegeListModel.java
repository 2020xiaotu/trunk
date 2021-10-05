package com.example.red_pakege.model;


import com.example.red_pakege.widget.CommonModel;

import java.math.BigDecimal;

public class RedPakegeListModel extends CommonModel {
    int id;
    String image;
    public RedPakegeListModel(int id, String image, String name, String tip, BigDecimal userWalletMinPrice) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.tip = tip;
        this.userWalletMinPrice = userWalletMinPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String name;
    String tip;
    BigDecimal userWalletMinPrice;

    public BigDecimal getUserWalletMinPrice() {
        return userWalletMinPrice;
    }

    public void setUserWalletMinPrice(BigDecimal userWalletMinPrice) {
        this.userWalletMinPrice = userWalletMinPrice;
    }

    public String getName() {
        return name;
    }

    public RedPakegeListModel(String name, String tip) {
        this.name = name;
        this.tip = tip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
