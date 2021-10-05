package com.example.new_application.bean;

public class MeetProblemEntity {

    /**
     * type : 0
     * explain : 程序bug
     */

    private String type;
    private String explain;
    private int status=0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
