package com.example.red_pakege.model;

import com.example.red_pakege.widget.CommonModel;

public class SaoLeiModel extends CommonModel {
    String qbName;
    String amount;
    String createdDate;
    String qbImage;
    boolean isMySelf;
    boolean isBoom;
    boolean isBestLuck;

    public SaoLeiModel(String qbName, String amount, String createdDate, String qbImage, boolean isMySelf, boolean isBestLuck) {
        this.qbName = qbName;
        this.amount = amount;
        this.createdDate = createdDate;
        this.qbImage = qbImage;
        this.isMySelf = isMySelf;
        this.isBestLuck = isBestLuck;
    }

    public boolean isBestLuck() {
        return isBestLuck;
    }

    public SaoLeiModel setBestLuck(boolean bestLuck) {
        isBestLuck = bestLuck;
        return this;
    }

    public boolean isBoom() {
        return isBoom;
    }

    public SaoLeiModel(String qbName, String amount, String createdDate, String qbImage, boolean isMySelf, boolean isBoom, boolean isBestLuck) {
        this.qbName = qbName;
        this.amount = amount;
        this.createdDate = createdDate;
        this.qbImage = qbImage;
        this.isMySelf = isMySelf;
        this.isBoom = isBoom;
        this.isBestLuck = isBestLuck;
    }

    public SaoLeiModel setBoom(boolean boom) {
        isBoom = boom;
        return this;
    }



    public String getQbImage() {
        return qbImage;
    }

    public SaoLeiModel setQbImage(String qbImage) {
        this.qbImage = qbImage;
        return this;
    }

    public String getQbName() {
        return qbName;
    }

    public SaoLeiModel setQbName(String qbName) {
        this.qbName = qbName;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public SaoLeiModel setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public SaoLeiModel setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public boolean isMySelf() {
        return isMySelf;
    }

    public SaoLeiModel setMySelf(boolean mySelf) {
        isMySelf = mySelf;
        return this;
    }
}
