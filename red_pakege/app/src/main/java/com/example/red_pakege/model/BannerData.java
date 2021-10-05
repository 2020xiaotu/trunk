package com.example.red_pakege.model;

public class BannerData {
    public String title;
    public String remark;
    public String url;
    public String href;
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public BannerData(String title, String remark, String url, String href, String id) {
        this.title = title;
        this.remark = remark;
        this.url = url;
        this.href = href;
        this.id = id;
    }

    public BannerData() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BannerData(String url) {
        this.url = url;
    }
}
