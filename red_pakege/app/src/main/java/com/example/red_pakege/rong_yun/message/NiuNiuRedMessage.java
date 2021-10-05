package com.example.red_pakege.rong_yun.message;

import android.os.Parcel;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
@MessageTag(value = "HB:Send", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class NiuNiuRedMessage extends MessageContent {
    //自定义的属性
    private String orderCode;//红包id
    private Integer hbType;//红包类型(跟hbName对应)
    private String hbName;//红包名(牛牛 扫雷....)
    private String hbMsg;//红包金额(格式:金额-红包的个数)

    private boolean isClicked =false;
    private String tip ="查看红包";
    private boolean isGain=false;
    private boolean isPastDue=false;
    private boolean isOver=false;
    private boolean isQB=true;
    public boolean isQB() {
        return isQB;
    }

    public NiuNiuRedMessage setQB(boolean QB) {
        isQB = QB;
        return this;
    }




    public boolean isGain() {
        return isGain;
    }

    public NiuNiuRedMessage setGain(boolean gain) {
        isGain = gain;
        return this;
    }

    public boolean isPastDue() {
        return isPastDue;
    }

    public NiuNiuRedMessage setPastDue(boolean pastDue) {
        isPastDue = pastDue;
        return this;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public NiuNiuRedMessage(String orderCode, Integer hbType, String hbName, String hbMsg) {
        this.orderCode = orderCode;
        this.hbType = hbType;
        this.hbName = hbName;
        this.hbMsg = hbMsg;
    }
    /*{"orderCode":"sss","hbName":"牛牛红包","hbType":"1","hbMsg":"120-11"}
     *
     * 实现 encode() 方法，该方法的功能是将消息属性封装成 json 串，
     * 再将 json 串转成 byte 数组，该方法会在发消息时调用，如下面示例代码：
     * */
    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("orderCode", this.getOrderCode());
            jsonObj.put("hbType",this.getHbType());
            jsonObj.put("hbName",this.getHbName());
            jsonObj.put("hbMsg",this.getHbMsg());

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
    public NiuNiuRedMessage(byte[] data) {
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
            if (jsonObj.has("hbType"))
                setHbType(jsonObj.optInt("hbType"));

            if (jsonObj.has("hbName"))
                setHbName(jsonObj.optString("hbName"));

            if (jsonObj.has("hbMsg"))
                setHbMsg(jsonObj.optString("hbMsg"));



        } catch (JSONException e) {
            Log.d("JSONException", e.getMessage());
        }
    }

    //给消息赋值。
    public NiuNiuRedMessage(Parcel in) {

        setOrderCode(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
        //这里可继续增加你消息的属性
        setHbType(ParcelUtils.readIntFromParcel(in));//该类为工具类，消息属性
        setHbName(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性RR
        setHbMsg(ParcelUtils.readFromParcel(in));//该类为工具类，消息属性
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<NiuNiuRedMessage> CREATOR = new Creator<NiuNiuRedMessage>() {

        @Override
        public NiuNiuRedMessage createFromParcel(Parcel source) {
            return new NiuNiuRedMessage(source);
        }

        @Override
        public NiuNiuRedMessage[] newArray(int size) {
            return new NiuNiuRedMessage[size];
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
        ParcelUtils.writeToParcel(dest, getHbType());
        ParcelUtils.writeToParcel(dest, getHbName());
        ParcelUtils.writeToParcel(dest, getHbMsg());

    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getHbName() {
        return hbName;
    }

    public void setHbName(String hbName) {
        this.hbName = hbName;
    }

    public String getHbMsg() {
        return hbMsg;
    }

    public void setHbMsg(String hbMsg) {
        this.hbMsg = hbMsg;
    }

    public Integer getHbType() {
        return hbType;
    }

    public void setHbType(Integer hbType) {
        this.hbType = hbType;
    }
}


