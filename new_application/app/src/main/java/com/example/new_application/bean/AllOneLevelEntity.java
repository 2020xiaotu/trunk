package com.example.new_application.bean;

import java.util.ArrayList;

public class AllOneLevelEntity {
    String name;
    boolean isOpen = true;
    ArrayList<AllTwoLevelEntity> childList = new ArrayList<>();

    public AllOneLevelEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public ArrayList<AllTwoLevelEntity> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<AllTwoLevelEntity> childList) {
        this.childList = childList;
    }
}
