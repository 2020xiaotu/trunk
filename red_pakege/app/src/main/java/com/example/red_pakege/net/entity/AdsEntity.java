package com.example.red_pakege.net.entity;

import java.util.List;

/**
 * created  by ganzhe on 2019/11/15.
 */
public class AdsEntity {


    /**
     * status : 200
     * message : 成功
     * data : [{"title":"视频广告","remark":"视频","img":"upload/images/20191113/1573643772780.png,upload/images/20191113/1573643786045.png","href":"http://www.xunbo678.com,http://www.ugliving.com","type":2,"location":7,"sort":1,"status":1,"clickNums":5,"startDate":"2019-11-13 00:00:00","endDate":"2019-11-30 00:00:00","id":8}]
     * rel : true
     */


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 视频广告
         * remark : 视频
         * img : upload/images/20191113/1573643772780.png,upload/images/20191113/1573643786045.png
         * href : http://www.xunbo678.com,http://www.ugliving.com
         * type : 2
         * location : 7
         * sort : 1
         * status : 1
         * clickNums : 5
         * startDate : 2019-11-13 00:00:00
         * endDate : 2019-11-30 00:00:00
         * id : 8
         */

        private String title;
        private String remark;
        private String img;
        private String href;
        private int type;
        private int location;
        private int sort;
        private int status;
        private int clickNums;
        private String startDate;
        private String endDate;
        private int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getClickNums() {
            return clickNums;
        }

        public void setClickNums(int clickNums) {
            this.clickNums = clickNums;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
