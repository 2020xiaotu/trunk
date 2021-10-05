package com.example.new_application.bean;

public class AppUploadEntity {

    /**
     * lastModifiedUser : sys
     * testFlightCode : 
     * versionCode : 2
     * createdDate : 1554875858000
     * isForce : 1
     * isDelete : 0
     * lastModifiedDate : 1628738160000
     * versionName : 1.0.1
     * downLoadUrl2 : 
     * description : 是否更新到最新版本？
     1.优化用户体验，修复已知bug
     2.如遇更新失败请到官网重新下载
     * id : 2
     * createdUser : ccc
     * downLoadUrl : http://172.18.165.16:8185/upload/images/20210528/本地.apk
     * systemType : 2
     */

    private AppVersionBean appVersion;
    /**
     * appVersion : {"lastModifiedUser":"sys","testFlightCode":"","versionCode":2,"createdDate":1554875858000,"isForce":1,"isDelete":0,"lastModifiedDate":1628738160000,"versionName":"1.0.1","downLoadUrl2":"","description":"是否更新到最新版本？\n1.优化用户体验，修复已知bug\n2.如遇更新失败请到官网重新下载","id":2,"createdUser":"ccc","downLoadUrl":"http://172.18.165.16:8185/upload/images/20210528/本地.apk","systemType":2}
     * status : success
     * message : 获取版本号成功
     */

    private String status;
    private String message;

    public AppVersionBean getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(AppVersionBean appVersion) {
        this.appVersion = appVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class AppVersionBean {
        private String lastModifiedUser;
        private String testFlightCode;
        private String versionCode;
        private String createdDate;
        private String isForce;
        private String isDelete;
        private String lastModifiedDate;
        private String versionName;
        private String downLoadUrl2;
        private String description;
        private String id;
        private String createdUser;
        private String downLoadUrl;
        private String systemType;

        public String getLastModifiedUser() {
            return lastModifiedUser;
        }

        public void setLastModifiedUser(String lastModifiedUser) {
            this.lastModifiedUser = lastModifiedUser;
        }

        public String getTestFlightCode() {
            return testFlightCode;
        }

        public void setTestFlightCode(String testFlightCode) {
            this.testFlightCode = testFlightCode;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getIsForce() {
            return isForce;
        }

        public void setIsForce(String isForce) {
            this.isForce = isForce;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(String lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getDownLoadUrl2() {
            return downLoadUrl2;
        }

        public void setDownLoadUrl2(String downLoadUrl2) {
            this.downLoadUrl2 = downLoadUrl2;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

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

        public String getDownLoadUrl() {
            return downLoadUrl;
        }

        public void setDownLoadUrl(String downLoadUrl) {
            this.downLoadUrl = downLoadUrl;
        }

        public String getSystemType() {
            return systemType;
        }

        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }
    }
}
