package com.example.new_application.bean;

import java.io.Serializable;

public class MineReleaseEntity implements Serializable {

    /**
     * id : 116
     * oneLevelClassification : 23
     * oneLevelClassificationName : null
     * twoLevelClassification : 25
     * twoLevelClassificationName : null
     * title : fsfsf
     * content : sssss
     * priceType : 1
     * price : 33
     * guaranteeType : 0
     * commissionType : null
     * attachment : null
     * checkStatus : 1
     * label : null
     * officialMark : null
     * remark : null
     * createdDate : 1630642478000
     * memberCollectMessageId : null
     */

    private String id;
    private String oneLevelClassification;
    private String oneLevelClassificationName;
    private String twoLevelClassification;
    private String twoLevelClassificationName;
    private String title;
    private String content;
    private int priceType;
    private String price;
    private int guaranteeType;
    private int commissionType;
    private String attachment;
    private String checkStatus;
    private String label;
    private String officialMark;
    private String remark;
    private String createdDate;
    private String orderCode;
    private String memberCollectMessageId;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(int guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public int getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
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
}
