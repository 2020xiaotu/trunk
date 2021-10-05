package com.example.new_application.bean;

public class RightMenuEntity {
    int resourceId;
    String name;

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RightMenuEntity(int resourceId, String name) {
        this.resourceId = resourceId;
        this.name = name;
    }
}
