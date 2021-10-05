package com.example.red_pakege.rong_yun.model;

import java.math.BigDecimal;

public class SaoLeiRoomModel {
    /**
     * status : 200
     * message : 成功
     * data : {"game":1,"typeId":2,"typeName":"扫雷","name":"即时通讯，支持群聊炒群，好友单聊","roomType":1,"notice":"榴莲炸了","listSort":1,"status":1,"image":"","hbUsefulTime":300,"hbNum":5,"fbMinPrice":10,"fbMaxPrice":30,"userWalletMinPrice":100,"odds":1.6,"startTime":"2019-11-05 08:00:00","endTime":"2020-11-05 23:59:00","isSpeak":1,"isHot":1,"isFl":0,"fbMinConsumption":0,"qbMinProfit":0,"id":1,"createdDate":"2019-11-15 20:24:08"}
     * rel : true
     */

    private int status;
    private String message;
    private DataBean data;
    private boolean rel;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }

    public static class DataBean {
        /**
         * game : 1
         * typeId : 2
         * typeName : 扫雷
         * name : 即时通讯，支持群聊炒群，好友单聊
         * roomType : 1
         * notice : 榴莲炸了
         * listSort : 1
         * status : 1
         * image : 
         * hbUsefulTime : 300
         * hbNum : 5
         * fbMinPrice : 10.0
         * fbMaxPrice : 30.0
         * userWalletMinPrice : 100
         * odds : 1.6
         * startTime : 2019-11-05 08:00:00
         * endTime : 2020-11-05 23:59:00
         * isSpeak : 1
         * isHot : 1
         * isFl : 0
         * fbMinConsumption : 0
         * qbMinProfit : 0
         * id : 1
         * createdDate : 2019-11-15 20:24:08
         */

        private int game;
        private int typeId;
        private String typeName;
        private String name;
        private int roomType;
        private String notice;
        private int listSort;
        private int status;
        private String image;
        private int hbUsefulTime;
        private int hbNum;
        private BigDecimal fbMinPrice;
        private BigDecimal fbMaxPrice;
        private int userWalletMinPrice;
        private BigDecimal odds;
        private String startTime;
        private String endTime;
        private int isSpeak;
        private int isHot;
        private int isFl;
        private int fbMinConsumption;
        private int qbMinProfit;
        private int id;
        private String createdDate;

        public int getGame() {
            return game;
        }

        public void setGame(int game) {
            this.game = game;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRoomType() {
            return roomType;
        }

        public void setRoomType(int roomType) {
            this.roomType = roomType;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public int getListSort() {
            return listSort;
        }

        public void setListSort(int listSort) {
            this.listSort = listSort;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getHbUsefulTime() {
            return hbUsefulTime;
        }

        public void setHbUsefulTime(int hbUsefulTime) {
            this.hbUsefulTime = hbUsefulTime;
        }

        public int getHbNum() {
            return hbNum;
        }

        public void setHbNum(int hbNum) {
            this.hbNum = hbNum;
        }

        public BigDecimal getFbMinPrice() {
            return fbMinPrice;
        }

        public void setFbMinPrice(BigDecimal fbMinPrice) {
            this.fbMinPrice = fbMinPrice;
        }

        public BigDecimal getFbMaxPrice() {
            return fbMaxPrice;
        }

        public void setFbMaxPrice(BigDecimal fbMaxPrice) {
            this.fbMaxPrice = fbMaxPrice;
        }

        public int getUserWalletMinPrice() {
            return userWalletMinPrice;
        }

        public void setUserWalletMinPrice(int userWalletMinPrice) {
            this.userWalletMinPrice = userWalletMinPrice;
        }

        public BigDecimal getOdds() {
            return odds;
        }

        public void setOdds(BigDecimal odds) {
            this.odds = odds;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getIsSpeak() {
            return isSpeak;
        }

        public void setIsSpeak(int isSpeak) {
            this.isSpeak = isSpeak;
        }

        public int getIsHot() {
            return isHot;
        }

        public void setIsHot(int isHot) {
            this.isHot = isHot;
        }

        public int getIsFl() {
            return isFl;
        }

        public void setIsFl(int isFl) {
            this.isFl = isFl;
        }

        public int getFbMinConsumption() {
            return fbMinConsumption;
        }

        public void setFbMinConsumption(int fbMinConsumption) {
            this.fbMinConsumption = fbMinConsumption;
        }

        public int getQbMinProfit() {
            return qbMinProfit;
        }

        public void setQbMinProfit(int qbMinProfit) {
            this.qbMinProfit = qbMinProfit;
        }

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
    }
    

}
