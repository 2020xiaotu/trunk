package com.example.new_application.bean;

import java.util.ArrayList;
import java.util.List;

public class HelpParentEntity {
    String name;
    int drawableId;
    boolean isOpen = false;
    List<HelpChildEntity> helpChildEntityArrayList = new ArrayList<>();

    public HelpParentEntity(String name,int drawableId, List<HelpChildEntity> helpChildEntityArrayList) {
        this.name = name;
        this.drawableId = drawableId;
        this.helpChildEntityArrayList = helpChildEntityArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public List<HelpChildEntity> getHelpChildEntityArrayList() {
        return helpChildEntityArrayList;
    }

    public void setHelpChildEntityArrayList(ArrayList<HelpChildEntity> helpChildEntityArrayList) {
        this.helpChildEntityArrayList = helpChildEntityArrayList;
    }
}
