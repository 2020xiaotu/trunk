package com.example.red_pakege.net.entity;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;

/**
 * created  by ganzhe on 2019/11/16.
 */
public class ParamUserEntity extends BaseEntity {

    /**
     * nickname : string
     * password : string
     * paypassword : string
     * sign : 123456789
     */

    private String nickname;
    private String password;
    private String paypassword;
    private String sign;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
