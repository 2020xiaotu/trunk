package com.example.new_application.bean;

public class   UserInfoEntity {


    /**
     * nickname : 侵袭半生电视剧的开始
     * image : upload/images/20210811/1628658950215.jpg
     * userType : 2
     * verifyStatus : 1
     * details : {"xylc":"<p>3</p>","aboutUs":"<p>1</p>","cxbz":"<p>2</p>"}
     * whatsapp : dsds
     * telegram : dsds
     * weixin : dsdsd
     * skype : dsd
     * username : xx1111
     * lockFlag : 0
     * id : 1376427347512148008
     * label : 都会死都会随很多事
     * qq : dsd
     * tokenInfo : 70fd8920807743838df231320d88adb5
     * verifyDate : 1628753097000
     */

    private String nickname;
    private String image;
    private String userType="1";
    private String verifyStatus;//1未认证,2审核中,3已认证,4认证未通过
    private String details;
    private String officialMark;
    private String verifyRemark;
    private String customerServiceLink;
    private String customerServiceId;
    private String earnestMoney;
    private String whatsapp;
    private String telegram="";
    private String weixin;
    private String skype;
    private String phone;
    private String username;
    private String lockFlag;
    private String id;
    private String label;
    private String qq;
    private String bat;
    private String potato;
    private String tokenInfo;
    private String verifyDate;
    private String memberCollectMessageId;

    //---------------------        我的足迹用到 ---------------
    boolean isCheck =false;//是否选中
    boolean isShow = false;//是否显示左侧删除

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getBat() {
        return bat;
    }

    public void setBat(String bat) {
        this.bat = bat;
    }

    public String getPotato() {
        return potato;
    }

    public void setPotato(String potato) {
        this.potato = potato;
    }

    public String getMemberCollectMessageId() {
        return memberCollectMessageId;
    }

    public void setMemberCollectMessageId(String memberCollectMessageId) {
        this.memberCollectMessageId = memberCollectMessageId;
    }

    public String getOfficialMark() {
        return officialMark;
    }

    public void setOfficialMark(String officialMark) {
        this.officialMark = officialMark;
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark;
    }

    public String getCustomerServiceLink() {
        return customerServiceLink;
    }

    public void setCustomerServiceLink(String customerServiceLink) {
        this.customerServiceLink = customerServiceLink;
    }

    public String getCustomerServiceId() {
        return customerServiceId;
    }

    public void setCustomerServiceId(String customerServiceId) {
        this.customerServiceId = customerServiceId;
    }

    public String getEarnestMoney() {
        return earnestMoney;
    }

    public void setEarnestMoney(String earnestMoney) {
        this.earnestMoney = earnestMoney;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(String tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }
}
