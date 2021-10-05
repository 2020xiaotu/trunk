package com.example.red_pakege.net.entity;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RechargeEntreyEntity extends BaseEntity {


    /**
     * data : {"message":"获取汇款支付方式成功","bankAllList":[{"image":"upload/images/20190918/1568813133660.jpg","createdDate":1574330963000,"isDelete":1,"name":"支付宝","id":3,"sort":2,"type":2,"rechargeBankList":[{"image":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1018346658,1138586997&fm=26&gp=0.jpg","bankAllTypeName":"支付宝","merchantCode":"","orderNo":11,"rechargeBankThirdTypeNetwayCode":"","lastModifiedDate":1573801111000,"isDelete":0,"bankName":"支付宝","down":100,"url":"https://www.baidu.com","bankAllId":3,"gradeStr":"0,1,2,3,4,5,6,7,8,9","rechargeBankThirdTypeName":"","dinpayPublicKey":"test","name":"修改测试人员","lastModifiedUser":"sys","id":6,"payNums":0,"up":50000,"account":"1234567890","bankAllType":2,"status":1,"createdDate":1573800936000,"createdUser":"sys","createdIp":"","lastModifiedIp":""},{"image":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1018346658,1138586997&fm=26&gp=0.jpg","bankAllTypeName":"支付宝","merchantCode":"","orderNo":11,"rechargeBankThirdTypeNetwayCode":"","lastModifiedDate":1573903935000,"isDelete":0,"bankName":"支付宝","down":100,"url":"https://www.baidu.com","bankAllId":3,"gradeStr":"0,1,2,3,4,5,6,7,8,9","createdDate":1573800936000,"rechargeBankThirdTypeName":"","dinpayPublicKey":"test","name":"修改测试","lastModifiedUser":"sys","id":9,"payNums":0,"up":50000,"account":"1234567890","createdUser":"sys","bankAllType":2,"status":1},{"bankAllTypeName":"支付宝","rechargeBankThirdTypeNetwayCode":"","createdIp":"","bankName":"支付宝333","down":100,"bankAllId":3,"gradeStr":"0,1,2,3,4,5,6,7,8,9","rechargeBankThirdTypeName":"","lastModifiedIp":"","lastModifiedUser":"sys","id":12,"up":50000,"createdUser":"sys","bankAllType":2,"image":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1018346658,1138586997&fm=26&gp=0.jpg","merchantCode":"","orderNo":11,"lastModifiedDate":1574912223000,"isDelete":1,"url":"https://www.baidu.com","createdDate":1574324783000,"name":"修改测试","payNums":0,"account":"1234567890","status":1}],"status":1}],"status":"success"}
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
         * message : 获取汇款支付方式成功
         * bankAllList : [{"image":"upload/images/20190918/1568813133660.jpg","createdDate":1574330963000,"isDelete":1,"name":"支付宝","id":3,"sort":2,"type":2,"rechargeBankList":[{"image":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1018346658,1138586997&fm=26&gp=0.jpg","bankAllTypeName":"支付宝","merchantCode":"","orderNo":11,"rechargeBankThirdTypeNetwayCode":"","lastModifiedDate":1573801111000,"isDelete":0,"bankName":"支付宝","down":100,"url":"https://www.baidu.com","bankAllId":3,"gradeStr":"0,1,2,3,4,5,6,7,8,9","rechargeBankThirdTypeName":"","dinpayPublicKey":"test","name":"修改测试人员","lastModifiedUser":"sys","id":6,"payNums":0,"up":50000,"account":"1234567890","bankAllType":2,"status":1},{"image":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1018346658,1138586997&fm=26&gp=0.jpg","bankAllTypeName":"支付宝","merchantCode":"","orderNo":11,"rechargeBankThirdTypeNetwayCode":"","lastModifiedDate":1573903935000,"isDelete":0,"bankName":"支付宝","down":100,"url":"https://www.baidu.com","bankAllId":3,"gradeStr":"0,1,2,3,4,5,6,7,8,9","createdDate":1573800936000,"rechargeBankThirdTypeName":"","dinpayPublicKey":"test","name":"修改测试","lastModifiedUser":"sys","id":9,"payNums":0,"up":50000,"account":"1234567890","createdUser":"sys","bankAllType":2,"status":1},{"bankAllTypeName":"支付宝","rechargeBankThirdTypeNetwayCode":"","createdIp":"","bankName":"支付宝333","down":100,"bankAllId":3,"gradeStr":"0,1,2,3,4,5,6,7,8,9","rechargeBankThirdTypeName":"","lastModifiedIp":"","lastModifiedUser":"sys","id":12,"up":50000,"createdUser":"sys","bankAllType":2,"image":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1018346658,1138586997&fm=26&gp=0.jpg","merchantCode":"","orderNo":11,"lastModifiedDate":1574912223000,"isDelete":1,"url":"https://www.baidu.com","createdDate":1574324783000,"name":"修改测试","payNums":0,"account":"1234567890","status":1}],"status":1}]
         * status : success
         */

        @SerializedName("message")
        private String messageX;
        @SerializedName("status")
        private String statusX;
        private List<BankAllListBean> bankAllList;

        public String getMessageX() {
            return messageX;
        }

        public void setMessageX(String messageX) {
            this.messageX = messageX;
        }

        public String getStatusX() {
            return statusX;
        }

        public void setStatusX(String statusX) {
            this.statusX = statusX;
        }

        public List<BankAllListBean> getBankAllList() {
            return bankAllList;
        }

        public void setBankAllList(List<BankAllListBean> bankAllList) {
            this.bankAllList = bankAllList;
        }

        public static class BankAllListBean {
            /**
             * image : upload/images/20190918/1568813133660.jpg
             * createdDate : 1574330963000
             * isDelete : 1
             * name : 支付宝
             * id : 3
             * sort : 2
             * type : 2
             * rechargeBankList : [{"image":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1018346658,1138586997&fm=26&gp=0.jpg","bankAllTypeName":"支付宝","merchantCode":"","orderNo":11,"rechargeBankThirdTypeNetwayCode":"","lastModifiedDate":1573801111000,"isDelete":0,"bankName":"支付宝","down":100,"url":"https://www.baidu.com","bankAllId":3,"gradeStr":"0,1,2,3,4,5,6,7,8,9","rechargeBankThirdTypeName":"","dinpayPublicKey":"test","name":"修改测试人员","lastModifiedUser":"sys","id":6,"payNums":0,"up":50000,"account":"1234567890","bankAllType":2,"status":1},{"image":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1018346658,1138586997&fm=26&gp=0.jpg","bankAllTypeName":"支付宝","merchantCode":"","orderNo":11,"rechargeBankThirdTypeNetwayCode":"","lastModifiedDate":1573903935000,"isDelete":0,"bankName":"支付宝","down":100,"url":"https://www.baidu.com","bankAllId":3,"gradeStr":"0,1,2,3,4,5,6,7,8,9","createdDate":1573800936000,"rechargeBankThirdTypeName":"","dinpayPublicKey":"test","name":"修改测试","lastModifiedUser":"sys","id":9,"payNums":0,"up":50000,"account":"1234567890","createdUser":"sys","bankAllType":2,"status":1},{"bankAllTypeName":"支付宝","rechargeBankThirdTypeNetwayCode":"","createdIp":"","bankName":"支付宝333","down":100,"bankAllId":3,"gradeStr":"0,1,2,3,4,5,6,7,8,9","rechargeBankThirdTypeName":"","lastModifiedIp":"","lastModifiedUser":"sys","id":12,"up":50000,"createdUser":"sys","bankAllType":2,"image":"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1018346658,1138586997&fm=26&gp=0.jpg","merchantCode":"","orderNo":11,"lastModifiedDate":1574912223000,"isDelete":1,"url":"https://www.baidu.com","createdDate":1574324783000,"name":"修改测试","payNums":0,"account":"1234567890","status":1}]
             * status : 1
             */

            private String image;
            private long createdDate;
            private int isDelete;
            private String name;
            private int id;
            private int sort;
            private int type;
            @SerializedName("status")
            private int statusX;
            private List<RechargeBankListBean> rechargeBankList;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public long getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(long createdDate) {
                this.createdDate = createdDate;
            }

            public int getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(int isDelete) {
                this.isDelete = isDelete;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public List<RechargeBankListBean> getRechargeBankList() {
                return rechargeBankList;
            }

            public void setRechargeBankList(List<RechargeBankListBean> rechargeBankList) {
                this.rechargeBankList = rechargeBankList;
            }

            public static class RechargeBankListBean {
                /**
                 * image : https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1018346658,1138586997&fm=26&gp=0.jpg
                 * bankAllTypeName : 支付宝
                 * merchantCode :
                 * orderNo : 11
                 * rechargeBankThirdTypeNetwayCode :
                 * lastModifiedDate : 1573801111000
                 * isDelete : 0
                 * bankName : 支付宝
                 * down : 100
                 * url : https://www.baidu.com
                 * bankAllId : 3
                 * gradeStr : 0,1,2,3,4,5,6,7,8,9
                 * rechargeBankThirdTypeName :
                 * dinpayPublicKey : test
                 * name : 修改测试人员
                 * lastModifiedUser : sys
                 * id : 6
                 * payNums : 0
                 * up : 50000
                 * account : 1234567890
                 * bankAllType : 2
                 * status : 1
                 * createdDate : 1573800936000
                 * createdUser : sys
                 * createdIp :
                 * lastModifiedIp :
                 */

                private String image;
                private String bankAllTypeName;
                private String merchantCode;
                private int orderNo;
                private String rechargeBankThirdTypeNetwayCode;
                private long lastModifiedDate;
                private int isDelete;
                private String bankName;
                private int down;
                private String url;
                private int bankAllId;
                private String gradeStr;
                private String rechargeBankThirdTypeName;
                private String dinpayPublicKey;
                private String name;
                private String lastModifiedUser;
                private int id;
                private int payNums;
                private int up;
                private String account;
                private int bankAllType;
                @SerializedName("status")
                private int statusX;
                private long createdDate;
                private String createdUser;
                private String createdIp;
                private String lastModifiedIp;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getBankAllTypeName() {
                    return bankAllTypeName;
                }

                public void setBankAllTypeName(String bankAllTypeName) {
                    this.bankAllTypeName = bankAllTypeName;
                }

                public String getMerchantCode() {
                    return merchantCode;
                }

                public void setMerchantCode(String merchantCode) {
                    this.merchantCode = merchantCode;
                }

                public int getOrderNo() {
                    return orderNo;
                }

                public void setOrderNo(int orderNo) {
                    this.orderNo = orderNo;
                }

                public String getRechargeBankThirdTypeNetwayCode() {
                    return rechargeBankThirdTypeNetwayCode;
                }

                public void setRechargeBankThirdTypeNetwayCode(String rechargeBankThirdTypeNetwayCode) {
                    this.rechargeBankThirdTypeNetwayCode = rechargeBankThirdTypeNetwayCode;
                }

                public long getLastModifiedDate() {
                    return lastModifiedDate;
                }

                public void setLastModifiedDate(long lastModifiedDate) {
                    this.lastModifiedDate = lastModifiedDate;
                }

                public int getIsDelete() {
                    return isDelete;
                }

                public void setIsDelete(int isDelete) {
                    this.isDelete = isDelete;
                }

                public String getBankName() {
                    return bankName;
                }

                public void setBankName(String bankName) {
                    this.bankName = bankName;
                }

                public int getDown() {
                    return down;
                }

                public void setDown(int down) {
                    this.down = down;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getBankAllId() {
                    return bankAllId;
                }

                public void setBankAllId(int bankAllId) {
                    this.bankAllId = bankAllId;
                }

                public String getGradeStr() {
                    return gradeStr;
                }

                public void setGradeStr(String gradeStr) {
                    this.gradeStr = gradeStr;
                }

                public String getRechargeBankThirdTypeName() {
                    return rechargeBankThirdTypeName;
                }

                public void setRechargeBankThirdTypeName(String rechargeBankThirdTypeName) {
                    this.rechargeBankThirdTypeName = rechargeBankThirdTypeName;
                }

                public String getDinpayPublicKey() {
                    return dinpayPublicKey;
                }

                public void setDinpayPublicKey(String dinpayPublicKey) {
                    this.dinpayPublicKey = dinpayPublicKey;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getLastModifiedUser() {
                    return lastModifiedUser;
                }

                public void setLastModifiedUser(String lastModifiedUser) {
                    this.lastModifiedUser = lastModifiedUser;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getPayNums() {
                    return payNums;
                }

                public void setPayNums(int payNums) {
                    this.payNums = payNums;
                }

                public int getUp() {
                    return up;
                }

                public void setUp(int up) {
                    this.up = up;
                }

                public String getAccount() {
                    return account;
                }

                public void setAccount(String account) {
                    this.account = account;
                }

                public int getBankAllType() {
                    return bankAllType;
                }

                public void setBankAllType(int bankAllType) {
                    this.bankAllType = bankAllType;
                }

                public int getStatusX() {
                    return statusX;
                }

                public void setStatusX(int statusX) {
                    this.statusX = statusX;
                }

                public long getCreatedDate() {
                    return createdDate;
                }

                public void setCreatedDate(long createdDate) {
                    this.createdDate = createdDate;
                }

                public String getCreatedUser() {
                    return createdUser;
                }

                public void setCreatedUser(String createdUser) {
                    this.createdUser = createdUser;
                }

                public String getCreatedIp() {
                    return createdIp;
                }

                public void setCreatedIp(String createdIp) {
                    this.createdIp = createdIp;
                }

                public String getLastModifiedIp() {
                    return lastModifiedIp;
                }

                public void setLastModifiedIp(String lastModifiedIp) {
                    this.lastModifiedIp = lastModifiedIp;
                }
            }
        }
    }
}
