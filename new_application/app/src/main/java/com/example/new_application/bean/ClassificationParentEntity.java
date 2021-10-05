package com.example.new_application.bean;

import java.util.List;

public class ClassificationParentEntity {

    /**
     * id : 23
     * name : 云账号
     * childList : [{"id":"32","name":"微信","isGuarantee":0,"picture":"upload/images/20210828/1630132471139.png","postNumber":3},{"id":"36","name":"CDN","isGuarantee":0,"picture":"upload/images/20210828/1630132585129.png","postNumber":3},{"id":"37","name":"聊天软件","isGuarantee":0,"picture":"upload/images/20210828/1630132613103.png","postNumber":2},{"id":"38","name":"飞利浦","isGuarantee":0,"picture":"upload/images/20210828/1630132643169.png","postNumber":0},{"id":"39","name":"书城","isGuarantee":0,"picture":"upload/images/20210828/1630132757864.png","postNumber":0},{"id":"40","name":"云小说","isGuarantee":0,"picture":"upload/images/20210828/1630132775957.png","postNumber":0},{"id":"24","name":"QQ","isGuarantee":0,"picture":"upload/images/20210828/1630131963734.png","postNumber":3},{"id":"25","name":"阿里云","isGuarantee":0,"picture":"upload/images/20210828/1630132450366.png","postNumber":0},{"id":"26","name":"腾讯云","isGuarantee":0,"picture":"upload/images/20210828/1630131992576.png","postNumber":0},{"id":"33","name":"服务器","isGuarantee":0,"picture":"upload/images/20210828/1630131835663.png","postNumber":0}]
     */

    private String id;
    private String name;
    /**
     * id : 32
     * name : 微信
     * isGuarantee : 0
     * picture : upload/images/20210828/1630132471139.png
     * postNumber : 3
     */

    private List<ChildListBean> childList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildListBean> getChildList() {
        return childList;
    }

    public void setChildList(List<ChildListBean> childList) {
        this.childList = childList;
    }

    public static class ChildListBean {
        private String id;
        private String name;
        private String isGuarantee;
        private String picture;
        private String postNumber;
        private boolean isCheck =false;




        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIsGuarantee() {
            return isGuarantee;
        }

        public void setIsGuarantee(String isGuarantee) {
            this.isGuarantee = isGuarantee;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getPostNumber() {
            return postNumber;
        }

        public void setPostNumber(String postNumber) {
            this.postNumber = postNumber;
        }
    }
}
