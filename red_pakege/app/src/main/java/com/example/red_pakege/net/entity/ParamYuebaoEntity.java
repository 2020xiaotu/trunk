package com.example.red_pakege.net.entity;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;

import java.io.Serializable;

/**
 * created  by ganzhe on 2019/11/16.
 */
public class ParamYuebaoEntity extends BaseEntity implements Serializable {


    /**
     * amountType : 0
     * sign : 123456789
     * source : 0 安卓;1 iso
     * token : string
     * user_id : string
     */

    private int amountType;
    private String sign;
    private String source;
    private String token;
    private String user_id;

    public int getAmountType() {
        return amountType;
    }

    public void setAmountType(int amountType) {
        this.amountType = amountType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
