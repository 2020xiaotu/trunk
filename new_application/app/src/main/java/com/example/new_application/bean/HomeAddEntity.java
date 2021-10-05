package com.example.new_application.bean;

public class HomeAddEntity {
    String name;
    int resourceId;
    int flag;// 1 运营商发布需求 2 服务商发布服务  3 担保 4 联合运营

    public HomeAddEntity(String name, int resourceId, int flag) {
        this.name = name;
        this.resourceId = resourceId;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
