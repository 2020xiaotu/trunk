package com.example.new_application.bean;

public class ClassificationDetailsChildEntity {
    boolean isOfficial = false;
    String name;

    public boolean isOfficial() {
        return isOfficial;
    }

    public void setOfficial(boolean official) {
        isOfficial = official;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClassificationDetailsChildEntity(String name) {
        this.name = name;
    }

    public ClassificationDetailsChildEntity() {
    }
}
