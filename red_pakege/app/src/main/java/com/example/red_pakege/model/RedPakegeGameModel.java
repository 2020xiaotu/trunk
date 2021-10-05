package com.example.red_pakege.model;

import com.example.red_pakege.widget.CommonModel;

public class RedPakegeGameModel extends CommonModel {
    String imageUrlOut;
    String imageUrlIn;
    String gameName;
    int childId;

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public RedPakegeGameModel(String imageUrlOut, String imageUrlIn, String gameName, int childId) {
        this.imageUrlOut = imageUrlOut;
        this.imageUrlIn = imageUrlIn;
        this.gameName = gameName;
        this.childId = childId;
    }

    public String getImageUrlIn() {
        return imageUrlIn;
    }

    public void setImageUrlIn(String imageUrlIn) {
        this.imageUrlIn = imageUrlIn;
    }

    public String getImageUrlOut() {
        return imageUrlOut;
    }

    public void setImageUrlOut(String imageUrlOut) {
        this.imageUrlOut = imageUrlOut;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public RedPakegeGameModel(String imageUrlOut, String gameName) {
        this.imageUrlOut = imageUrlOut;
        this.gameName = gameName;
    }
}
