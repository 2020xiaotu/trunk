package com.example.red_pakege.model;

import com.example.red_pakege.widget.CommonModel;

public class OtherGameModel extends CommonModel {
    String imageUrl;
    int imageId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public OtherGameModel(int imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public OtherGameModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
