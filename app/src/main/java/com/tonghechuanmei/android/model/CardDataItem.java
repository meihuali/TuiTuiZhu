/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/21/18 3:46 PM
 */

package com.tonghechuanmei.android.model;

public class CardDataItem {
    private String imagePath;
    private String userName;
    private int likeNum;
    private int imageNum;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    @Override
    public String toString() {
        return "CardDataItem{" +
                "imagePath='" + imagePath + '\'' +
                ", userName='" + userName + '\'' +
                ", likeNum=" + likeNum +
                ", imageNum=" + imageNum +
                '}';
    }
}