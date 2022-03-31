package com.prabhatkushwaha.launcher.model

import android.graphics.drawable.Drawable

data class AppModel(
    val appName: String,
    val packageName: String,
    val appIcon: Drawable,
    val versionCode: String,
    val versionName: String
)