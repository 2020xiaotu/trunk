package com.example.new_application.bean;

import java.util.List;

public class ChooseServerParentEntity {

    /**
     * id : 4
     * name : 测试
     * picture : null
     * childList : [{"id":"7","name":"二级","picture":"upload/images/20210805/1628146259552.jpg","childList":[]}]
     */

    private String id;
    private String name;
    private String picture;
    private boolean isOpen = false;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    /**
     * id : 7
     * name : 二级
     * picture : upload/images/20210805/1628146259552.jpg
     * childList : []
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
        private String picture;
        private List<?> childList;
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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public List<?> getChildList() {
            return childList;
        }

        public void setChildList(List<?> childList) {
            this.childList = childList;
        }
    }
}
