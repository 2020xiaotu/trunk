package com.example.new_application.bean;

public class StoreDetailsEntity {

    /**
     * id : 1376427347512148030
     * username : kg0005
     * nickname : 服务
     * image : upload/images/20210827/1630046910996.jpeg
     * userType : 2
     * facilitatorType : null
     * verifyStatus : 3
     * verifyDate : 1630046846000
     * appType : null
     * lockFlag : 0
     * phone : null
     * telegram : 
     * skype : 
     * whatsapp : 
     * qq : 884848
     * weixin : 7788
     * label : 爱上
     * officialMark : 13,15,14
     * verifyStringroduction : null
     * verifyRemark : 值得拥有
     * details : {"xylc":"&lt;p&gt;日范儿复健科考虑到非健康的&lt;/p&gt;","aboutUs":"这么早起来吃早饭吃个","cxbz":"&lt;p&gt;瑞瑞即可sad繁花似锦&lt;/p&gt;"}
     * tokenInfo : null
     * memberCollectMessageId : null
     * isGuarantee : 1
     */

    private MemberInfoVoBean memberInfoVo;
    /**
     * memberInfoVo : {"id":1376427347512148030,"username":"kg0005","nickname":"服务","image":"upload/images/20210827/1630046910996.jpeg","userType":2,"facilitatorType":null,"verifyStatus":3,"verifyDate":1630046846000,"appType":null,"lockFlag":0,"phone":null,"telegram":"","skype":"","whatsapp":"","qq":"884848","weixin":"7788","label":"爱上","officialMark":"13,15,14","verifyStringroduction":null,"verifyRemark":"值得拥有","details":"{\"xylc\":\"&lt;p&gt;日范儿复健科考虑到非健康的&lt;/p&gt;\",\"aboutUs\":\"这么早起来吃早饭吃个\",\"cxbz\":\"&lt;p&gt;瑞瑞即可sad繁花似锦&lt;/p&gt;\"}","tokenInfo":null,"memberCollectMessageId":null,"isGuarantee":1}
     * memberCollectResourceMessage : 0
     */

    private String memberCollectResourceMessage;

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

    public static class MemberInfoVoBean {
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
        private String label = "";
        private String officialMark="";
        private String verifyStringroduction;
        private String verifyRemark;
        private String details;
        private String tokenInfo;
        private String memberCollectMessageId;
        private String isGuarantee;
        private String earnestMoney;
        private String guaranteeType;//交易模式(0：自行交易,1：担保交易)

        public String getGuaranteeType() {
            return guaranteeType;
        }

        public void setGuaranteeType(String guaranteeType) {
            this.guaranteeType = guaranteeType;
        }

        public String getEarnestMoney() {
            return earnestMoney;
        }

        public void setEarnestMoney(String earnestMoney) {
            this.earnestMoney = earnestMoney;
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
            return label.replace("，",",");
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getOfficialMark() {
            return officialMark.replace("，",",").trim();
        }

        public void setOfficialMark(String officialMark) {
            this.officialMark = officialMark;
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
}
