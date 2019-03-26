/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-11-27 下午7:10
 */

package com.designer.library.component.picker;

/**
 * @author Simon Lee
 * @e-mail jmlixiaomeng@163.com
 * @github https://github.com/Simon-Leeeeeeeee/SLWidget
 * @createdTime 2018-05-17
 */
public interface PickAdapter {

    /**
     * 返回数据总个数
     */
    int getCount();

    /**
     * 返回一条对应index的数据
     */
    String getItem(int position);

}
