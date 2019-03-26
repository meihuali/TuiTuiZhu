/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-4 上午10:28
 */

package com.tonghechuanmei.android.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

public class UserAddressEntity {

    private String msg;

    public UserAddressEntity setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private String id;
        private String name;
        private String address;
        private String cityArea;
        private long createTime;
        private String userId;
        private String phone;
        private String tel;
        private String isDefault;
        private String userName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCityArea() {
            return cityArea;
        }

        public void setCityArea(String cityArea) {
            this.cityArea = cityArea;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }


        public String getAddressData() {
            String s1 = (TextUtils.isEmpty(getCityArea())) ? "" : getCityArea();
            String s2 = (TextUtils.isEmpty(getAddress())) ? "" : getAddress();
            return s1 + s2;
        }
    }
}
