package com.example.new_application.bean;

public class UnReadMessageEntity {

    /**
     * typeAll : 1
     * type2 : 0
     * user_id : 1376427347512148008
     * type1 : 1
     * type0 : 0
     */
//(type0:系统消息数,type1:交易消息数,type2:合作消息数,typeAll:总消息数)
    private String typeAll;
    private String type2;
    private String user_id;
    private String type1;
    private String type0;

    public String getTypeAll() {
        return typeAll;
    }

    public void setTypeAll(String typeAll) {
        this.typeAll = typeAll;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType0() {
        return type0;
    }

    public void setType0(String type0) {
        this.type0 = type0;
    }
}
