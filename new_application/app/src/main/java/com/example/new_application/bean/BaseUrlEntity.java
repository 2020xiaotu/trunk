package com.example.new_application.bean;


public class BaseUrlEntity {


    /**
     * id : 1
     * createdUser : qixi
     * createdDate : 1632382617000
     * createdIp : null
     * lastModifiedUser : qixi
     * lastModifiedDate : 1632382954000
     * lastModifiedIp : null
     * isDelete : 0
     * domain : http://8.210.29.222:8385/zixunWeb/
     * listsort : 1
     * status : 1
     * type : 0
     * flag : 1
     */

    private String id;
    private String createdUser;
    private String createdDate;
    private String createdIp;
    private String lastModifiedUser;
    private String lastModifiedDate;
    private String lastModifiedIp;
    private String isDelete;
    private String domain;
    private String listsort;
    private String status;
    private String type;
    private String flag;
    boolean isCheck = false;
    private long startTime;
    private long endTime;
    private boolean isSuccess=true;
    public boolean isCheck() {
        return isCheck;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getListsort() {
        return listsort;
    }

    public void setListsort(String listsort) {
        this.listsort = listsort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
