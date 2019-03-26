/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/3/18 4:46 PM
 */

package com.tonghechuanmei.android.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.tonghechuanmei.android.R

fun Activity.previewMedia(position: Int = 0, paths: List<String>) {
    val mediaList = mutableListOf<LocalMedia>()

    paths.forEach {
        mediaList.add(LocalMedia().apply { this.path = it })
    }

    PictureSelector.create(this).themeStyle(R.style.picture_default_style)
        .openExternalPreview(position, mediaList)
}

fun Fragment.previewMedia(position: Int = 0, paths: List<String>) {
    val mediaList = mutableListOf<LocalMedia>()

    paths.forEach {
        mediaList.add(LocalMedia().apply { this.path = it })
    }

    PictureSelector.create(this).themeStyle(R.style.picture_default_style)
        .openExternalPreview(position, mediaList)
}

fun Activity.previewMedia(path: String) {
    val mediaList = mutableListOf<LocalMedia>()
    mediaList.add(LocalMedia().apply { this.path = path })
    PictureSelector.create(this).themeStyle(R.style.picture_default_style)
        .openExternalPreview(0, mediaList)
}

fun Fragment.previewMedia(path: String) {
    val mediaList = mutableListOf<LocalMedia>()
    mediaList.add(LocalMedia().apply { this.path = path })
    PictureSelector.create(this).themeStyle(R.style.picture_default_style)
        .openExternalPreview(0, mediaList)
}