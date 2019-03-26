/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.widget

import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import java.util.*

fun CommonTabLayout.setTabEntity(vararg title: String) {

    val customTabEntities = ArrayList<CustomTabEntity>()

    for (aTitle in title) {
        customTabEntities.add(TabEntity(aTitle))
    }

    setTabData(customTabEntities)
}


class TabEntity(val title: String, val selectedIcon: Int = 0, val unselectedIcon: Int = 0) : CustomTabEntity {

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unselectedIcon
    }
}
