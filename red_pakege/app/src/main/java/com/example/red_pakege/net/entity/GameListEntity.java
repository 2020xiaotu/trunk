package com.example.red_pakege.net.entity;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;

import java.util.List;

/**
 * created  by ganzhe on 2019/11/14.
 */
public class GameListEntity extends BaseEntity {



    /**
     * status : 200
     * message : 成功
     * data : [{"id":1,"typeName":"禁抢","image1":"","image2":""},{"id":2,"typeName":"扫雷","image1":"","image2":""},{"id":3,"typeName":"牛牛","image1":"","image2":""}]
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
         * id : 1
         * typeName : 禁抢
         * image1 :
         * image2 :
         */

        private int id;
        private String typeName;
        private String image1;
        private String image2;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }
    }
}


