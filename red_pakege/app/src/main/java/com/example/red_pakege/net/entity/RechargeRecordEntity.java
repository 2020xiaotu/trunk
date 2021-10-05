package com.example.red_pakege.net.entity;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RechargeRecordEntity extends BaseEntity {


    /**
     * data : {"total":4,"rows":[{"id":19,"createdDate":"2019-12-07 15:52:50","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-07 18:05:43","lastModifiedIp":"","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":251,"bankName":"我的微信","userRealName":"jony","depositDate":"2019-12-07 15:52:50","status":1,"bankAllId":2,"bank_id":16,"rechargeBankThirdTypeName":"","type":11,"typeName":"个人微信","depositBankName":"我的微信","depositName":"pg123456","depositAccount":"412038685","isRead":1,"orderCode":"CZ2019120715525023332629","source":0,"domain":"","remark":"个人微信","isTest":0},{"id":5,"createdDate":"2019-12-07 12:54:34","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-07 18:02:52","lastModifiedIp":"","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":100,"bankName":"建行","userRealName":"jony","depositDate":"2019-12-07 12:54:34","status":1,"bankAllId":1,"bank_id":6,"rechargeBankThirdTypeName":"","type":10,"typeName":"个人银行转账","depositBankName":"建行","depositName":"迅博pg123456","depositAccount":"1234567890","isRead":1,"orderCode":"CZ2019120712543416172641","source":0,"domain":"","remark":"个人银行转账","isTest":0},{"id":3,"createdDate":"2019-12-06 11:32:47","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-06 16:26:16","lastModifiedIp":"","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":100,"bankName":"支付宝","userRealName":"jony","depositDate":"2019-12-06 11:32:47","status":1,"bankAllId":3,"bank_id":6,"rechargeBankThirdTypeName":"","type":12,"typeName":"个人支付宝","depositBankName":"支付宝","depositName":"修改测试人员","depositAccount":"1234567890","isRead":1,"orderCode":"CZ2019120611324726986530","source":0,"domain":"","remark":"个人支付宝","isTest":0},{"id":2,"createdDate":"2019-12-04 12:46:01","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-07 18:00:55","lastModifiedIp":"","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":100,"bankName":"支付宝","userRealName":"修改测试人员1","depositDate":"2019-12-04 12:46:01","status":1,"bankAllId":3,"bank_id":6,"rechargeBankThirdTypeName":"","type":12,"typeName":"个人支付宝","depositBankName":"支付宝","depositName":"修改测试人员","depositAccount":"1234567890","isRead":1,"orderCode":"CZ2019120412460106012743","source":0,"domain":"","remark":"个人支付宝","isTest":0}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 4
         * rows : [{"id":19,"createdDate":"2019-12-07 15:52:50","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-07 18:05:43","lastModifiedIp":"","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":251,"bankName":"我的微信","userRealName":"jony","depositDate":"2019-12-07 15:52:50","status":1,"bankAllId":2,"bank_id":16,"rechargeBankThirdTypeName":"","type":11,"typeName":"个人微信","depositBankName":"我的微信","depositName":"pg123456","depositAccount":"412038685","isRead":1,"orderCode":"CZ2019120715525023332629","source":0,"domain":"","remark":"个人微信","isTest":0},{"id":5,"createdDate":"2019-12-07 12:54:34","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-07 18:02:52","lastModifiedIp":"","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":100,"bankName":"建行","userRealName":"jony","depositDate":"2019-12-07 12:54:34","status":1,"bankAllId":1,"bank_id":6,"rechargeBankThirdTypeName":"","type":10,"typeName":"个人银行转账","depositBankName":"建行","depositName":"迅博pg123456","depositAccount":"1234567890","isRead":1,"orderCode":"CZ2019120712543416172641","source":0,"domain":"","remark":"个人银行转账","isTest":0},{"id":3,"createdDate":"2019-12-06 11:32:47","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-06 16:26:16","lastModifiedIp":"","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":100,"bankName":"支付宝","userRealName":"jony","depositDate":"2019-12-06 11:32:47","status":1,"bankAllId":3,"bank_id":6,"rechargeBankThirdTypeName":"","type":12,"typeName":"个人支付宝","depositBankName":"支付宝","depositName":"修改测试人员","depositAccount":"1234567890","isRead":1,"orderCode":"CZ2019120611324726986530","source":0,"domain":"","remark":"个人支付宝","isTest":0},{"id":2,"createdDate":"2019-12-04 12:46:01","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-07 18:00:55","lastModifiedIp":"","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":100,"bankName":"支付宝","userRealName":"修改测试人员1","depositDate":"2019-12-04 12:46:01","status":1,"bankAllId":3,"bank_id":6,"rechargeBankThirdTypeName":"","type":12,"typeName":"个人支付宝","depositBankName":"支付宝","depositName":"修改测试人员","depositAccount":"1234567890","isRead":1,"orderCode":"CZ2019120412460106012743","source":0,"domain":"","remark":"个人支付宝","isTest":0}]
         */

        private int total;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * id : 19
             * createdDate : 2019-12-07 15:52:50
             * createdIp :
             * createdUser : sys
             * lastModifiedDate : 2019-12-07 18:05:43
             * lastModifiedIp :
             * lastModifiedUser : sys
             * isDelete : 0
             * user_id : 21
             * price : 251
             * bankName : 我的微信
             * userRealName : jony
             * depositDate : 2019-12-07 15:52:50
             * status : 1
             * bankAllId : 2
             * bank_id : 16
             * rechargeBankThirdTypeName :
             * type : 11
             * typeName : 个人微信
             * depositBankName : 我的微信
             * depositName : pg123456
             * depositAccount : 412038685
             * isRead : 1
             * orderCode : CZ2019120715525023332629
             * source : 0
             * domain :
             * remark : 个人微信
             * isTest : 0
             */

            private int id;
            private String createdDate;
            private String createdIp;
            private String createdUser;
            private String lastModifiedDate;
            private String lastModifiedIp;
            private String lastModifiedUser;
            private int isDelete;
            private int user_id;
            private int price;
            private String bankName;
            private String userRealName;
            private String depositDate;
            @SerializedName("status")
            private int statusX;
            private int bankAllId;
            private int bank_id;
            private String rechargeBankThirdTypeName;
            private int type;
            private String typeName;
            private String depositBankName;
            private String depositName;
            private String depositAccount;
            private int isRead;
            private String orderCode;
            private int source;
            private String domain;
            private String remark;
            private int isTest;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getCreatedUser() {
                return createdUser;
            }

            public void setCreatedUser(String createdUser) {
                this.createdUser = createdUser;
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

            public String getLastModifiedUser() {
                return lastModifiedUser;
            }

            public void setLastModifiedUser(String lastModifiedUser) {
                this.lastModifiedUser = lastModifiedUser;
            }

            public int getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(int isDelete) {
                this.isDelete = isDelete;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getUserRealName() {
                return userRealName;
            }

            public void setUserRealName(String userRealName) {
                this.userRealName = userRealName;
            }

            public String getDepositDate() {
                return depositDate;
            }

            public void setDepositDate(String depositDate) {
                this.depositDate = depositDate;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public int getBankAllId() {
                return bankAllId;
            }

            public void setBankAllId(int bankAllId) {
                this.bankAllId = bankAllId;
            }

            public int getBank_id() {
                return bank_id;
            }

            public void setBank_id(int bank_id) {
                this.bank_id = bank_id;
            }

            public String getRechargeBankThirdTypeName() {
                return rechargeBankThirdTypeName;
            }

            public void setRechargeBankThirdTypeName(String rechargeBankThirdTypeName) {
                this.rechargeBankThirdTypeName = rechargeBankThirdTypeName;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getDepositBankName() {
                return depositBankName;
            }

            public void setDepositBankName(String depositBankName) {
                this.depositBankName = depositBankName;
            }

            public String getDepositName() {
                return depositName;
            }

            public void setDepositName(String depositName) {
                this.depositName = depositName;
            }

            public String getDepositAccount() {
                return depositAccount;
            }

            public void setDepositAccount(String depositAccount) {
                this.depositAccount = depositAccount;
            }

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getIsTest() {
                return isTest;
            }

            public void setIsTest(int isTest) {
                this.isTest = isTest;
            }
        }
    }
}
