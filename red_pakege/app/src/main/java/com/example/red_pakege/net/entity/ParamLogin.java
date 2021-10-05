package com.example.red_pakege.net.entity;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;

/**
 * created  by ganzhe on 2019/11/19.
 */
public class ParamLogin extends BaseEntity {


    /**
     * nickname : ganzhe14
     * password : ganzhe123456
     * sign : 123456789
     * source : 0
     */

    private String nickname;
    private String password;
    private String sign;
    private String source;

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
}
