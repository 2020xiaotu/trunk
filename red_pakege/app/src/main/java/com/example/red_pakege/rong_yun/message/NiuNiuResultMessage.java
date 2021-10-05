package com.example.red_pakege.rong_yun.message;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@MessageTag(value = "HB:ResultNiuNiu", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class NiuNiuResultMessage extends MessageContent {
    //自定义的属性
    private String orderCode;//红包id
/*    private Long userId;//发包用户ID
    private Boolean isRobot;//是否机器人*/
    private String userName;//发包用户名
    private String avatar;//头像

    private Integer bankerNiuJi; //庄家niuJi
    private Integer bankerWinCount;//庄家赢的个数
    private Integer playerWinCount;//玩家赢的个数
    private Integer all;//是否通吃    -1:通赔  0:不显示  1 :通知

    public NiuNiuResultMessage(String orderCode,/* Long userId, Boolean isRobot,*/ String userName, String avatar, Integer bankerNiuJi, Integer bankerWinCount, Integer playerWinCount, Integer all) {
        this.orderCode = orderCode;
   /*     this.userId = userId;
        this.isRobot = isRobot;*/
        this.userName = userName;
        this.avatar = avatar;
        this.bankerNiuJi = bankerNiuJi;
        this.bankerWinCount = bankerWinCount;
        this.playerWinCount = playerWinCount;
        this.all = all;
    }

    /*{"orderCode":"sss","userId":"11","isRobot":"1","userName":"垃圾车","avatar":"http://bssl.duitang.com/uploads/item/201611/27/20161127214104_eZdhj.jpeg","bankerNiuJi":"9","bankerWinCount":"9","playerWinCount":"0","all":"1"}
     *
     * 实现 encode() 方法，该方法的功能是将消息属性封装成 json 串，
     * 再将 json 串转成 byte 数组，该方法会在发消息时调用，如下面示例代码：
     * */
    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("orderCode", this.getOrderCode());
//            jsonObj.put("userId",this.getUserId());
//            jsonObj.put("isRobot",this.getRobot());
            jsonObj.put("userName",this.getUserName());
            jsonObj.put("avatar",this.getAvatar());
            jsonObj.put("bankerNiuJi",this.getBankerNiuJi());
            jsonObj.put("bankerWinCount",this.getBankerWinCount());
            jsonObj.put("playerWinCount",this.getPlayerWinCount());
            jsonObj.put("all",this.getAll());

        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 覆盖父类的 MessageContent(byte[] data) 构造方法，该方法将对收到的消息进行解析，
     * 先由 byte 转成 json 字符串，再将 json 中内容取出赋值给消息属性。
     * */
    public NiuNiuResultMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("orderCode"))
                setOrderCode(jsonObj.optString("orderCode"));
         /*   if (jsonObj.has("userId"))
                setUserId(jsonObj.optLong("userId"));*/
/*
            if (jsonObj.has("isRobot"))
                setRobot(jsonObj.optBoolean("isRobot"));*/

            if (jsonObj.has("userName"))
                setUserName(jsonObj.optString("userName"));

            if (jsonObj.has("avatar"))
                setAvatar(jsonObj.optString("avatar"));

            if (jsonObj.has("bankerNiuJi"))
                setBankerNiuJi(jsonObj.optInt("bankerNiuJi"));

            if (jsonObj.has("bankerWinCount"))
                setBankerWinCount(jsonObj.optInt("bankerWinCount"));

            if (jsonObj.has("playerWinCount"))
                setPlayerWinCount(jsonObj.optInt("playerWinCount"));

            if (jsonObj.has("all"))
                setAll(jsonObj.optInt("all"));

        } catch (JSONException e) {
            Log.d("JSONException", e.getMessage());
        }
    }

    //给消息赋值。
    public NiuNiuResultMessage(Parcel in) {

        setOrderCode(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        //这里可继续增加你消息的属性
//        setUserId(ParcelUtils.readLongFromParcel(in));//该类为工具类，消息属性
        setUserName(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性RR
        setAvatar(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        setBankerNiuJi(ParcelUtils.readIntFromParcel(in));//该类为工具类，消息属性
        setBankerWinCount(ParcelUtils.readIntFromParcel(in));//该类为工具类，消息属性
        setPlayerWinCount(ParcelUtils.readIntFromParcel(in));//该类为工具类，消息属性
        setAll(ParcelUtils.readIntFromParcel(in));//该类为工具类，消息属性
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<NiuNiuResultMessage> CREATOR = new Creator<NiuNiuResultMessage>() {

        @Override
        public NiuNiuResultMessage createFromParcel(Parcel source) {
            return new NiuNiuResultMessage(source);
        }

        @Override
        public NiuNiuResultMessage[] newArray(int size) {
            return new NiuNiuResultMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将类的数据写入外部提供的 Parcel 中。
     * @param dest  对象被写入的 Parcel。
     * @param flags 对象如何被写入的附加标志。
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        ParcelUtils.writeToParcel(dest, getOrderCode());
//        ParcelUtils.writeToParcel(dest, getUserId());
  /*      boolean b = getRobot().booleanValue();
        ParcelUtils.writeToParcel(dest, b?1:0);*/
        ParcelUtils.writeToParcel(dest, getUserName());
        ParcelUtils.writeToParcel(dest, getAvatar());
        ParcelUtils.writeToParcel(dest, getBankerNiuJi());
        ParcelUtils.writeToParcel(dest, getBankerWinCount());
        ParcelUtils.writeToParcel(dest, getPlayerWinCount());
        ParcelUtils.writeToParcel(dest, getAll());
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

/*    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getRobot() {
        return isRobot;
    }

    public void setRobot(Boolean robot) {
        isRobot = robot;
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getBankerNiuJi() {
        return bankerNiuJi;
    }

    public void setBankerNiuJi(Integer bankerNiuJi) {
        this.bankerNiuJi = bankerNiuJi;
    }

    public Integer getBankerWinCount() {
        return bankerWinCount;
    }

    public void setBankerWinCount(Integer bankerWinCount) {
        this.bankerWinCount = bankerWinCount;
    }

    public Integer getPlayerWinCount() {
        return playerWinCount;
    }

    public void setPlayerWinCount(Integer playerWinCount) {
        this.playerWinCount = playerWinCount;
    }

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }
}


