package com.example.new_application.bean;

import java.io.Serializable;

public class ServerDetailsEntity implements Serializable {

    /**
     * id : 1376427347512147996
     * username : xdd001
     * nickname : 喜多多
     * image : upload/images/app/20210806/1628230624180.jpg
     * userType : 1
     * facilitatorType : null
     * verifyStatus : 2
     * verifyDate : 1629528174000
     * appType : null
     * lockFlag : 0
     * phone : null
     * telegram : 22343
     * skype : 324
     * whatsapp : 234
     * qq : 
     * weixin : 
     * label : 需求标签1
     * verifyStringroduction : 
     * verifyRemark : null
     * details : {"xylc":"协议流程","aboutUs":"刚刚一个个","cxbz":"诚信保证"}
     * tokenInfo : null
     * memberCollectMessageId : null
     * isGuarantee : 1
     */

    private MemberInfoVoBean memberInfoVo;
    /**
     * memberInfoVo : {"id":1376427347512147996,"username":"xdd001","nickname":"喜多多","image":"upload/images/app/20210806/1628230624180.jpg","userType":1,"facilitatorType":null,"verifyStatus":2,"verifyDate":1629528174000,"appType":null,"lockFlag":0,"phone":null,"telegram":"22343","skype":"324","whatsapp":"234","qq":"","weixin":"","label":"需求标签1","verifyStringroduction":"","verifyRemark":null,"details":"{\"xylc\":\"协议流程\",\"aboutUs\":\"刚刚一个个\",\"cxbz\":\"诚信保证\"}","tokenInfo":null,"memberCollectMessageId":null,"isGuarantee":1}
     * memberCollectResourceMessage : 0
     * resourceMessage : {"id":"4","oneLevelClassificationName":"测试2","twoLevelClassificationName":"分类而二","title":"234555","content":"34324324","priceType":null,"price":null,"checkStatus":null,"label":"订单","officialMark":"12","remark":null,"createdDate":1628757455000,"memberCollectMessageId":null}
     */

    private String memberCollectResourceMessage;
    /**
     * id : 4
     * oneLevelClassificationName : 测试2
     * twoLevelClassificationName : 分类而二
     * title : 234555
     * content : 34324324
     * priceType : null
     * price : null
     * checkStatus : null
     * label : 订单
     * officialMark : 12
     * remark : null
     * createdDate : 1628757455000
     * memberCollectMessageId : null
     */

    private ResourceMessageBean resourceMessage;

    public MemberInfoVoBean getMemberInfoVo() {
        return memberInfoVo;
    }

    public void setMemberInfoVo(MemberInfoVoBean memberInfoVo) {
        this.memberInfoVo = memberInfoVo;
    }

    public String getMemberCollectResourceMessage() {
        return memberCollectResourceMessage;
    }

    public void setMemberCollectResourceMessage(String memberCollectResourceMessage) {
        this.memberCollectResourceMessage = memberCollectResourceMessage;
    }

    public ResourceMessageBean getResourceMessage() {
        return resourceMessage;
    }

    public void setResourceMessage(ResourceMessageBean resourceMessage) {
        this.resourceMessage = resourceMessage;
    }

    public static class MemberInfoVoBean implements Serializable{
        private String id;
        private String username;
        private String nickname;
        private String image;
        private String userType;
        private String facilitatorType;
        private String verifyStatus;
        private String verifyDate;
        private String appType;
        private String lockFlag;
        private String phone;
        private String telegram;
        private String skype;
        private String whatsapp;
        private String qq;
        private String weixin;
        private String bat;
        private String potato;
        private String label="";
        private String verifyStringroduction;
        private String verifyRemark;
        private String details;
        private String tokenInfo;
        private String officialMark="";
        private String memberCollectMessageId;
        private String isGuarantee;
        //---------------------        我的足迹用到 ---------------
        boolean isCheck =false;//是否选中
        boolean isShow = false;//是否显示左侧删除

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
        public String getOfficialMark() {
            return officialMark.replace("，",",").trim();
        }

        public void setOfficialMark(String officialMark) {
            this.officialMark = officialMark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
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

        public String getFacilitatorType() {
            return facilitatorType;
        }

        public void setFacilitatorType(String facilitatorType) {
            this.facilitatorType = facilitatorType;
        }

        public String getVerifyStatus() {
            return verifyStatus;
        }

        public void setVerifyStatus(String verifyStatus) {
            this.verifyStatus = verifyStatus;
        }

        public String getVerifyDate() {
            return verifyDate;
        }

        public void setVerifyDate(String verifyDate) {
            this.verifyDate = verifyDate;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getLockFlag() {
            return lockFlag;
        }

        public void setLockFlag(String lockFlag) {
            this.lockFlag = lockFlag;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTelegram() {
            return telegram;
        }

        public void setTelegram(String telegram) {
            this.telegram = telegram;
        }

        public String getSkype() {
            return skype;
        }

        public void setSkype(String skype) {
            this.skype = skype;
        }

        public String getWhatsapp() {
            return whatsapp;
        }

        public void setWhatsapp(String whatsapp) {
            this.whatsapp = whatsapp;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getLabel() {
            return label.replace("，",",").trim();
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getVerifyStringroduction() {
            return verifyStringroduction;
        }

        public void setVerifyStringroduction(String verifyStringroduction) {
            this.verifyStringroduction = verifyStringroduction;
        }

        public String getVerifyRemark() {
            return verifyRemark;
        }

        public void setVerifyRemark(String verifyRemark) {
            this.verifyRemark = verifyRemark;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getTokenInfo() {
            return tokenInfo;
        }

        public void setTokenInfo(String tokenInfo) {
            this.tokenInfo = tokenInfo;
        }

        public String getMemberCollectMessageId() {
            return memberCollectMessageId;
        }

        public void setMemberCollectMessageId(String memberCollectMessageId) {
            this.memberCollectMessageId = memberCollectMessageId;
        }

        public String getIsGuarantee() {
            return isGuarantee;
        }

        public void setIsGuarantee(String isGuarantee) {
            this.isGuarantee = isGuarantee;
        }
    }

    public static class ResourceMessageBean implements Serializable{
        private String id;
        private String oneLevelClassificationName;
        private String twoLevelClassificationName;
        private String oneLevelClassification;
        private String twoLevelClassification;
        private String title;
        private String content;
        private String priceType;
        private String attachment;
        private String price="";
        private String guaranteeType;//交易模式(0：自行交易,1：担保交易)
        private String commissionType;
        private String checkStatus;
        private String label;
        private String officialMark;
        private String remark;
        private String createdDate;
        private String orderCode;
        private String memberCollectMessageId;

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getOneLevelClassification() {
            return oneLevelClassification;
        }

        public void setOneLevelClassification(String oneLevelClassification) {
            this.oneLevelClassification = oneLevelClassification;
        }

        public String getTwoLevelClassification() {
            return twoLevelClassification;
        }

        public void setTwoLevelClassification(String twoLevelClassification) {
            this.twoLevelClassification = twoLevelClassification;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getGuaranteeType() {
            return guaranteeType;
        }

        public void setGuaranteeType(String guaranteeType) {
            this.guaranteeType = guaranteeType;
        }

        public String getCommissionType() {
            return commissionType;
        }

        public void setCommissionType(String commissionType) {
            this.commissionType = commissionType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOneLevelClassificationName() {
            return oneLevelClassificationName;
        }

        public void setOneLevelClassificationName(String oneLevelClassificationName) {
            this.oneLevelClassificationName = oneLevelClassificationName;
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
}
