package com.uyt.ying.yuan.uuuu.iuymn.mkjnb.activity.live;

import com.uyt.ying.yuan.R;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.Utils;

public enum GameTypeEnum {

    KUAISAN(1,Utils.getString(R.string.kuaisan)),
    SSC(2,Utils.getString(R.string.shishicai)),
    RACE(3,Utils.getString(R.string.saiche)),
    MARKSIX(4,Utils.getString(R.string.liuhecai)),
    DANDAN(5,Utils.getString(R.string.pcdandan2)),
    HAPPY8(6,Utils.getString(R.string.kuaile8)),
    LUCKFARM(7,Utils.getString(R.string.xingyunnongchang)),
    HAPPY10(8,Utils.getString(R.string.kuaileshi)),
    XUANWU(9,Utils.getString(R.string.shiyixuanwu)),
    LIVESHOP(1212, Utils.getString(R.string.zhibogoucai));



    /**
     * 状态值
     */
    private int value;
    /**
     * 类型描述
     */
    private String description;

    GameTypeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static GameTypeEnum valueOf(long value){
        for (GameTypeEnum typeEnume:GameTypeEnum.values()){
            if (typeEnume.getValue()==value){
                return typeEnume;
            }
        }
        return null;
    }


}
