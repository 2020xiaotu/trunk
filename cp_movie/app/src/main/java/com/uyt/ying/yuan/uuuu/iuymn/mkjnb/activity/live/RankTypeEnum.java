package com.uyt.ying.yuan.uuuu.iuymn.mkjnb.activity.live;

public enum RankTypeEnum  {

    GIFT(1,"礼物榜"),
    CHOCK(2,"中奖榜"),
    QY(3,"邀请榜"),
    ZX(4,"专享榜");

    private int value;
    private String des;


    RankTypeEnum(int value, String des) {
        this.value = value;
        this.des = des;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


    public static RankTypeEnum valueOf(int value){
        for (RankTypeEnum typeEnume:RankTypeEnum.values()){
            if (typeEnume.getValue()==value){
                return typeEnume;
            }
        }
        return null;
    }



}
