package com.example.new_application.bean;

import java.util.ArrayList;

public class ClassificationDetailsEntity {
    boolean isCheck =false;
    boolean isShow = false;//是否显示左侧删除
    ArrayList<ClassificationDetailsChildEntity> classificationDetailsChildEntityArrayList;
    /**
     * id : 77
     * oneLevelClassification : 23
     * oneLevelClassificationName : null
     * twoLevelClassification : 24
     * twoLevelClassificationName : null
     * title : 全球QQ
     * content : 全球经济发展趋势分析报告
     * priceType : 3
     * price : null
     * guaranteeType : 0
     * attachment : upload/images/app/20210830/1630291844871.jpg
     * checkStatus : 2
     * label : null
     * officialMark : null
     * remark : null
     * createdDate : 1630291901000
     * memberCollectMessageId : null
     */

    private String id;
    private String userType;
    private String oneLevelClassification;
    private String oneLevelClassificationName;
    private String twoLevelClassification;
    private String twoLevelClassificationName;
    private String title;
    private String content;
    private String priceType;//价格类型(1：一口价，2：范围价格，3：议价)
    private String price;
    private String guaranteeType;//交易模式(0：自行交易,1：担保交易)
    private String attachment;
    private String checkStatus;
    private String label;
    private String officialMark;
    private String remark;
    private String createdDate;
    private String memberCollectMessageId;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOneLevelClassification() {
        return oneLevelClassification;
    }

    public void setOneLevelClassification(String oneLevelClassification) {
        this.oneLevelClassification = oneLevelClassification;
    }

    public String getOneLevelClassificationName() {
        return oneLevelClassificationName;
    }

    public void setOneLevelClassificationName(String oneLevelClassificationName) {
        this.oneLevelClassificationName = oneLevelClassificationName;
    }

    public String getTwoLevelClassification() {
        return twoLevelClassification;
    }

    public void setTwoLevelClassification(String twoLevelClassification) {
        this.twoLevelClassification = twoLevelClassification;
    }

    public String getTwoLevelClassificationName() {
        return twoLevelClassificationName;
    }

    public void setTwoLevelClassificationName(String twoLevelClassificationName) {
        this.twoLevelClassificationName = twoLevelClassificationName;
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

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOfficialMark() {
        return officialMark;
    }

    public void setOfficialMark(String officialMark) {
        this.officialMark = officialMark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getMemberCollectMessageId() {
        return memberCollectMessageId;
    }

    public void setMemberCollectMessageId(String memberCollectMessageId) {
        this.memberCollectMessageId = memberCollectMessageId;
    }


    public ArrayList<ClassificationDetailsChildEntity> getClassificationDetailsChildEntityArrayList() {
        return classificationDetailsChildEntityArrayList;
    }

    public void setClassificationDetailsChildEntityArrayList(ArrayList<ClassificationDetailsChildEntity> classificationDetailsChildEntityArrayList) {
        this.classificationDetailsChildEntityArrayList = classificationDetailsChildEntityArrayList;
    }
}
