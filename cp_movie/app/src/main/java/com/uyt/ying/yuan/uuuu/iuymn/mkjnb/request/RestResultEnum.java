package com.uyt.ying.yuan.uuuu.iuymn.mkjnb.request;

/**
 * Created by Administrator on 2018/4/28.
 */
public enum  RestResultEnum {
    SUCCESS(WebServiceConstant.CODE_DEFAULT, WebServiceConstant.INFO_DEFAULT),//请求成功
    FAILED("11", "请求失败"),//请求失败
    TOKEN_ERROR(WebServiceConstant.CODE_ERROR_99, WebServiceConstant.ERROR_CODE_TOKEN_ERROR),//token校验失败！
    END("", "");

    private String code;//操作状态码
    private String info;//操作状态描述

    RestResultEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
