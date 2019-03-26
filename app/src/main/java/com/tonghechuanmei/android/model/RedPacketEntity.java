/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-14 下午3:38
 */

package com.tonghechuanmei.android.model;

import androidx.databinding.BaseObservable;

/**
 * Author : majingcheng
 * Date : 2018/9/22
 */
public class RedPacketEntity extends BaseObservable {
    private boolean isClick;   //能否点击
    private boolean isSelect;    //是否选中

    private int money;      //1 10 50 100 200 500

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getPrice() {
        return "¥" + String.valueOf(money);
    }

    public boolean getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }
}
