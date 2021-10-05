package com.example.red_pakege.model;

import com.example.red_pakege.widget.CommonModel;

public class ReportModel extends CommonModel {


    String amount;
    String content;



    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ReportModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReportModel(String amount, String content) {
        this.amount = amount;
        this.content = content;
    }
}
