/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.base

import android.app.Application
import com.designer.library.base.Library.app

object Library {
    var app: Application? = null
}


fun getApp(): Application {
    return app!!
}
