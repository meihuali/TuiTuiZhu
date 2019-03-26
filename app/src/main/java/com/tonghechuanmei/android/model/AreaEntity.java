/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-4 上午9:32
 */

package com.tonghechuanmei.android.model;

import java.util.List;

/**
 * Author     : shandirong
 * Date       : 2018/7/11 10:37
 */
public class AreaEntity {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String firstLetter;
        private String name;
        private String parentId;
        private int isSpecialArea;
        private int level;
        private Object number;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getIsSpecialArea() {
            return isSpecialArea;
        }

        public void setIsSpecialArea(int isSpecialArea) {
            this.isSpecialArea = isSpecialArea;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Object getNumber() {
            return number;
        }

        public void setNumber(Object number) {
            this.number = number;
        }
    }
}
