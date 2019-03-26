/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/21/18 3:46 PM
 */

package com.tonghechuanmei.android.model;

import androidx.databinding.BaseObservable;

/**
 * Author     : shandirong
 * Date       : 2018/11/17 16:50
 */
public class TaskPublishEntity extends BaseObservable {
    private String name;

    public TaskPublishEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name + "111111";
    }

    public void setName(String name) {
        this.name = name;
    }
}
