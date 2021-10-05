package com.example.red_pakege.model;

import com.example.red_pakege.widget.CommonModel;

public class ChildShowMoreModel extends CommonModel {
    String title;
    String amount;
    String content;
    String amountRight;
    String contentRight;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ChildShowMoreModel(String title, String amount, String content,  String amountRight, String contentRight) {
        this.title = title;
        this.amount = amount;
        this.content = content;
        this.amountRight = amountRight;
        this.contentRight = contentRight;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getAmountRight() {
        return amountRight;
    }

    public void setAmountRight(String amountRight) {
        this.amountRight = amountRight;
    }

    public String getContentRight() {
        return contentRight;
    }

    public void setContentRight(String contentRight) {
        this.contentRight = contentRight;
    }
}
