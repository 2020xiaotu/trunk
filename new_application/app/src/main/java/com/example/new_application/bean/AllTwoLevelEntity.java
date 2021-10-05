package com.example.new_application.bean;

public class AllTwoLevelEntity {
    String id;
    String name;
    boolean isCheck = false;
    private int guaranteeType;//交易模式(0：自行交易,1：担保交易)
    public AllTwoLevelEntity(String id, String name) {
        this.id = id;
        this.name = name;

    }

    public int getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(int guaranteeType) {
        this.guaranteeType = guaranteeType;
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



    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
