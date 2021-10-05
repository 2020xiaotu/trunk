package com.example.red_pakege.model;

import com.example.red_pakege.widget.CommonModel;

public class ChildPlayerModel extends CommonModel {
    String imageUrl;
    String name;
    String sex;
    String id;
    String agentNum;
    String playerNum;
    String yongjinAmount;



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentNum() {
        return agentNum;
    }

    public void setAgentNum(String agentNum) {
        this.agentNum = agentNum;
    }

    public String getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(String playerNum) {
        this.playerNum = playerNum;
    }

    public String getYongjinAmount() {
        return yongjinAmount;
    }

    public void setYongjinAmount(String yongjinAmount) {
        this.yongjinAmount = yongjinAmount;
    }

    public ChildPlayerModel(String imageUrl, String name, String sex, String id, String agentNum, String playerNum, String yongjinAmount) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.sex = sex;
        this.id = id;
        this.agentNum = agentNum;
        this.playerNum = playerNum;
        this.yongjinAmount = yongjinAmount;
    }

    public ChildPlayerModel(String name, String sex, String id, String agentNum, String playerNum, String yongjinAmount) {
        this.name = name;
        this.sex = sex;
        this.id = id;
        this.agentNum = agentNum;
        this.playerNum = playerNum;
        this.yongjinAmount = yongjinAmount;
    }
}
