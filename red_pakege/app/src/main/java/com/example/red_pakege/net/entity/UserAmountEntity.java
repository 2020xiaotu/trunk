package com.example.red_pakege.net.entity;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;

public class UserAmountEntity extends BaseEntity {


    /**
     * data : {"amount":0,"commission":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * amount : 0
         * commission : 0
         */

        private int amount;
        private int commission;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getCommission() {
            return commission;
        }

        public void setCommission(int commission) {
            this.commission = commission;
        }
    }
}
