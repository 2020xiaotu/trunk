package com.example.red_pakege.net.entity;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.google.gson.annotations.SerializedName;

/**
 * created  by ganzhe on 2019/11/16.
 */
public class YuebaoInfoEntity extends BaseEntity {


    /**
     * data : {"message":"success","settingVo":{"status":0,"jiZhunRate":0,"hongYunRate":0,"zcyhRare":0,"zclsRate":0,"jiZhunAmount":0,"hongYunAmount":0},"status":"success"}
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
         * message : success
         * settingVo : {"status":0,"jiZhunRate":0,"hongYunRate":0,"zcyhRare":0,"zclsRate":0,"jiZhunAmount":0,"hongYunAmount":0}
         * status : success
         */

        @SerializedName("message")
        private String messageX;
        private SettingVoBean settingVo;
        @SerializedName("status")
        private String statusX;

        public String getMessageX() {
            return messageX;
        }

        public void setMessageX(String messageX) {
            this.messageX = messageX;
        }

        public SettingVoBean getSettingVo() {
            return settingVo;
        }

        public void setSettingVo(SettingVoBean settingVo) {
            this.settingVo = settingVo;
        }

        public String getStatusX() {
            return statusX;
        }

        public void setStatusX(String statusX) {
            this.statusX = statusX;
        }

        public static class SettingVoBean {
            /**
             * status : 0
             * jiZhunRate : 0
             * hongYunRate : 0
             * zcyhRare : 0
             * zclsRate : 0
             * jiZhunAmount : 0
             * hongYunAmount : 0
             */

            @SerializedName("status")
            private int statusX;
            private int jiZhunRate;
            private int hongYunRate;
            private int zcyhRare;
            private int zclsRate;
            private int jiZhunAmount;
            private int hongYunAmount;

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public int getJiZhunRate() {
                return jiZhunRate;
            }

            public void setJiZhunRate(int jiZhunRate) {
                this.jiZhunRate = jiZhunRate;
            }

            public int getHongYunRate() {
                return hongYunRate;
            }

            public void setHongYunRate(int hongYunRate) {
                this.hongYunRate = hongYunRate;
            }

            public int getZcyhRare() {
                return zcyhRare;
            }

            public void setZcyhRare(int zcyhRare) {
                this.zcyhRare = zcyhRare;
            }

            public int getZclsRate() {
                return zclsRate;
            }

            public void setZclsRate(int zclsRate) {
                this.zclsRate = zclsRate;
            }

            public int getJiZhunAmount() {
                return jiZhunAmount;
            }

            public void setJiZhunAmount(int jiZhunAmount) {
                this.jiZhunAmount = jiZhunAmount;
            }

            public int getHongYunAmount() {
                return hongYunAmount;
            }

            public void setHongYunAmount(int hongYunAmount) {
                this.hongYunAmount = hongYunAmount;
            }
        }
    }
}
