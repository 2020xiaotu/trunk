package com.example.red_pakege.model;

import com.example.red_pakege.widget.CommonModel;

public class MessageFragmentMedel extends CommonModel {
    String title;
    String content;
    String imageUrl;

    public MessageFragmentMedel(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
