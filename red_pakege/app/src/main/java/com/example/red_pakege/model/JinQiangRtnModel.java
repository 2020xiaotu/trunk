package com.example.red_pakege.model;

import com.example.red_pakege.widget.CommonModel;

import java.util.Objects;

public class JinQiangRtnModel extends CommonModel {
    int num;
    int status =0;
    int type;//1 红包个数 2 雷数(用于控制同时同选中多个item还是只能选中一个)

    public int getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JinQiangRtnModel)) return false;
        JinQiangRtnModel that = (JinQiangRtnModel) o;
        return getNum() == that.getNum() &&
                getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNum(), getType());
    }

    public JinQiangRtnModel setType(int type) {
        this.type = type;
        return this;
    }

    public JinQiangRtnModel(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public JinQiangRtnModel setNum(int num) {
        this.num = num;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public JinQiangRtnModel setStatus(int status) {
        this.status = status;
        return this;
    }

    public JinQiangRtnModel(int num, int status) {
        this.num = num;
        this.status = status;
    }
}
