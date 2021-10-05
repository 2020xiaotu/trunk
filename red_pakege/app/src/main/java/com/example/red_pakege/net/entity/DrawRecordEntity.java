package com.example.red_pakege.net.entity;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrawRecordEntity extends BaseEntity {


    /**
     * data : {"total":3,"rows":[{"id":4,"createdDate":"2019-12-09 19:44:56","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-09 19:44:56","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":100,"bankCard":"6222024402027245111","bankName":"东莞银行","status":0,"represent":"wenjia.g","type":0,"isRead":0,"ordercode":"TX2019120919445594970278","source":0,"isTest":0,"flgUser":0},{"id":3,"createdDate":"2019-12-09 16:41:18","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-09 16:41:18","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":20,"bankCard":"6222024402027245996","bankName":"高丽银行","status":0,"represent":"高丽人的支行","type":0,"isRead":0,"ordercode":"TX2019120916411756592556","source":0,"isTest":0,"flgUser":0},{"id":2,"createdDate":"2019-12-09 14:57:18","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-09 14:57:18","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":50,"bankCard":"6222024402027245996","bankName":"高丽银行","status":0,"represent":"高丽人的支行","type":0,"isRead":0,"ordercode":"TX2019120914571819911357","source":0,"isTest":0,"flgUser":0}]}
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
         * total : 3
         * rows : [{"id":4,"createdDate":"2019-12-09 19:44:56","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-09 19:44:56","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":100,"bankCard":"6222024402027245111","bankName":"东莞银行","status":0,"represent":"wenjia.g","type":0,"isRead":0,"ordercode":"TX2019120919445594970278","source":0,"isTest":0,"flgUser":0},{"id":3,"createdDate":"2019-12-09 16:41:18","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-09 16:41:18","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":20,"bankCard":"6222024402027245996","bankName":"高丽银行","status":0,"represent":"高丽人的支行","type":0,"isRead":0,"ordercode":"TX2019120916411756592556","source":0,"isTest":0,"flgUser":0},{"id":2,"createdDate":"2019-12-09 14:57:18","createdIp":"","createdUser":"sys","lastModifiedDate":"2019-12-09 14:57:18","lastModifiedUser":"sys","isDelete":0,"user_id":21,"price":50,"bankCard":"6222024402027245996","bankName":"高丽银行","status":0,"represent":"高丽人的支行","type":0,"isRead":0,"ordercode":"TX2019120914571819911357","source":0,"isTest":0,"flgUser":0}]
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
             * id : 4
             * createdDate : 2019-12-09 19:44:56
             * createdIp :
             * createdUser : sys
             * lastModifiedDate : 2019-12-09 19:44:56
             * lastModifiedUser : sys
             * isDelete : 0
             * user_id : 21
             * price : 100
             * bankCard : 6222024402027245111
             * bankName : 东莞银行
             * status : 0
             * represent : wenjia.g
             * type : 0
             * isRead : 0
             * ordercode : TX2019120919445594970278
             * source : 0
             * isTest : 0
             * flgUser : 0
             */

            private int id;
            private String createdDate;
            private String createdIp;
            private String createdUser;
            private String lastModifiedDate;
            private String lastModifiedUser;
            private int isDelete;
            private int user_id;
            private int price;
            private String bankCard;
            private String bankName;
            @SerializedName("status")
            private int statusX;
            private String represent;
            private int type;
            private int isRead;
            private String ordercode;
            private int source;
            private int isTest;
            private int flgUser;

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

            public String getBankCard() {
                return bankCard;
            }

            public void setBankCard(String bankCard) {
                this.bankCard = bankCard;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public String getRepresent() {
                return represent;
            }

            public void setRepresent(String represent) {
                this.represent = represent;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }

            public String getOrdercode() {
                return ordercode;
            }

            public void setOrdercode(String ordercode) {
                this.ordercode = ordercode;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }

            public int getIsTest() {
                return isTest;
            }

            public void setIsTest(int isTest) {
                this.isTest = isTest;
            }

            public int getFlgUser() {
                return flgUser;
            }

            public void setFlgUser(int flgUser) {
                this.flgUser = flgUser;
            }
        }
    }
}
