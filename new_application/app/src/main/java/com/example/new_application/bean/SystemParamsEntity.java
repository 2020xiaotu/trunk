package com.example.new_application.bean;

public class SystemParamsEntity {


    /**
     * id : 1
     * createdUser : sys
     * createdDate : 1627711299000
     * createdIp : null
     * lastModifiedUser : qixi
     * lastModifiedDate : 1631262615000
     * lastModifiedIp : null
     * isDelete : 0
     * name : 飞鸽
     * systemSwitch : 0
     * chatRoomSwitch : 1
     * mobileNumSwitch : 1
     * virtualPhoneRegister : 1
     * isGuarantee : 1
     * noticeInfo : 111
     * onlineCustomer : https://chat60.live800.com/live800/chatClient/chatbox.jsp?companyID=51016&configID=726&enterurl=E4BAA7E59381E6A682E8BFB0Live800E5AE98E696B9E7BD91E7AB99
     * qqCustomer : 1
     * appAdImage : upload/images/20210823/1629717611271.jpg
     * appAdImageDurationSeconds : 5
     * imageUrl : http://8.210.29.222:8385/
     * zkCode : zixun
     * systemParameterInfoMap : {"telegramDomain":"xWjHXSqKzc/njzFD/jxUKQ==","telegramToken":"lihmMUWIXhrEC/s0Of3yMw==","telegramTokenRemark":"纸飞机机器人token(aes加密)","downLoadPageUrl":"http://www.baidu.com","downLoadPageUrlRemark":"下载页面地址","telegramDomainRemark":"纸飞机机器人域名(aes加密)"}
     */

    private String id;
    private String createdUser;
    private String createdDate;
    private String createdIp;
    private String lastModifiedUser;
    private String lastModifiedDate;
    private String lastModifiedIp;
    private String isDelete;
    private String name;
    private String systemSwitch;
    private String chatRoomSwitch;
    private String mobileNumSwitch;
    private String virtualPhoneRegister;
    private String isGuarantee;
    private String noticeInfo;
    private String onlineCustomer;
    private String qqCustomer;
    private String appAdImage;
    private Integer appAdImageDurationSeconds;
    private String imageUrl;
    private String zkCode;
    /**
     * telegramDomain : xWjHXSqKzc/njzFD/jxUKQ==
     * telegramToken : lihmMUWIXhrEC/s0Of3yMw==
     * telegramTokenRemark : 纸飞机机器人token(aes加密)
     * downLoadPageUrl : http://www.baidu.com
     * downLoadPageUrlRemark : 下载页面地址
     * telegramDomainRemark : 纸飞机机器人域名(aes加密)
     */

    private SystemParameterInfoMapBean systemParameterInfoMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(String lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedIp() {
        return lastModifiedIp;
    }

    public void setLastModifiedIp(String lastModifiedIp) {
        this.lastModifiedIp = lastModifiedIp;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSystemSwitch() {
        return systemSwitch;
    }

    public void setSystemSwitch(String systemSwitch) {
        this.systemSwitch = systemSwitch;
    }

    public String getChatRoomSwitch() {
        return chatRoomSwitch;
    }

    public void setChatRoomSwitch(String chatRoomSwitch) {
        this.chatRoomSwitch = chatRoomSwitch;
    }

    public String getMobileNumSwitch() {
        return mobileNumSwitch;
    }

    public void setMobileNumSwitch(String mobileNumSwitch) {
        this.mobileNumSwitch = mobileNumSwitch;
    }

    public String getVirtualPhoneRegister() {
        return virtualPhoneRegister;
    }

    public void setVirtualPhoneRegister(String virtualPhoneRegister) {
        this.virtualPhoneRegister = virtualPhoneRegister;
    }

    public String getIsGuarantee() {
        return isGuarantee;
    }

    public void setIsGuarantee(String isGuarantee) {
        this.isGuarantee = isGuarantee;
    }

    public String getNoticeInfo() {
        return noticeInfo;
    }

    public void setNoticeInfo(String noticeInfo) {
        this.noticeInfo = noticeInfo;
    }

    public String getOnlineCustomer() {
        return onlineCustomer;
    }

    public void setOnlineCustomer(String onlineCustomer) {
        this.onlineCustomer = onlineCustomer;
    }

    public String getQqCustomer() {
        return qqCustomer;
    }

    public void setQqCustomer(String qqCustomer) {
        this.qqCustomer = qqCustomer;
    }

    public String getAppAdImage() {
        return appAdImage;
    }

    public void setAppAdImage(String appAdImage) {
        this.appAdImage = appAdImage;
    }

    public Integer getAppAdImageDurationSeconds() {
        return appAdImageDurationSeconds;
    }

    public void setAppAdImageDurationSeconds(Integer appAdImageDurationSeconds) {
        this.appAdImageDurationSeconds = appAdImageDurationSeconds;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getZkCode() {
        return zkCode;
    }

    public void setZkCode(String zkCode) {
        this.zkCode = zkCode;
    }

    public SystemParameterInfoMapBean getSystemParameterInfoMap() {
        return systemParameterInfoMap;
    }

    public void setSystemParameterInfoMap(SystemParameterInfoMapBean systemParameterInfoMap) {
        this.systemParameterInfoMap = systemParameterInfoMap;
    }

    public static class SystemParameterInfoMapBean {
        private String telegramDomain;
        private String telegramToken;
        private String telegramTokenRemark;
        private String downLoadPageUrl;
        private String downLoadPageUrlRemark;
        private String telegramDomainRemark;
        private String downLoadShareContent;

        public String getDownLoadShareContent() {
            return downLoadShareContent;
        }

        public void setDownLoadShareContent(String downLoadShareContent) {
            this.downLoadShareContent = downLoadShareContent;
        }

        public String getTelegramDomain() {
            return telegramDomain;
        }

        public void setTelegramDomain(String telegramDomain) {
            this.telegramDomain = telegramDomain;
        }

        public String getTelegramToken() {
            return telegramToken;
        }

        public void setTelegramToken(String telegramToken) {
            this.telegramToken = telegramToken;
        }

        public String getTelegramTokenRemark() {
            return telegramTokenRemark;
        }

        public void setTelegramTokenRemark(String telegramTokenRemark) {
            this.telegramTokenRemark = telegramTokenRemark;
        }

        public String getDownLoadPageUrl() {
            return downLoadPageUrl;
        }

        public void setDownLoadPageUrl(String downLoadPageUrl) {
            this.downLoadPageUrl = downLoadPageUrl;
        }

        public String getDownLoadPageUrlRemark() {
            return downLoadPageUrlRemark;
        }

        public void setDownLoadPageUrlRemark(String downLoadPageUrlRemark) {
            this.downLoadPageUrlRemark = downLoadPageUrlRemark;
        }

        public String getTelegramDomainRemark() {
            return telegramDomainRemark;
        }

        public void setTelegramDomainRemark(String telegramDomainRemark) {
            this.telegramDomainRemark = telegramDomainRemark;
        }
    }
}
