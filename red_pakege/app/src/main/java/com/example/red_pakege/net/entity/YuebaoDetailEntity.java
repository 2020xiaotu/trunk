package com.example.red_pakege.net.entity;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;

import java.util.List;

/**
 * created  by ganzhe on 2019/11/16.
 */
public class YuebaoDetailEntity extends BaseEntity {

    /**
     * data : {"rows":[{}],"total":0}
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
         * rows : [{}]
         * total : 0
         */

        private int total;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
        }
    }
}
