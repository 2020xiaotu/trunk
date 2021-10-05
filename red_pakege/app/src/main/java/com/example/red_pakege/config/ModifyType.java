package com.example.red_pakege.config;

public enum ModifyType {

    AVATAR("修改头像", 1),
    NICKNAME("修改昵称", 2),
    USERSIGN("修改个性签名", 3),
    SEX("修改性别", 4),
    LOGINPWD("修改登录密码", 5),
    PAYPWD("修改支付密码", 6),
    BANKCARD("修改绑定银行卡", 7);

    private String name;
    private int index;

    ModifyType(String name, int index) {
        this.name = name;
        this.index = index;
    }
    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }


}
