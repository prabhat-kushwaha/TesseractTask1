package com.prabhatkushwaha.tesseractassessmenttask1.data.model

import android.graphics.drawable.Drawable

data class AppDataModel(
    val appName: String,
    val packageName: String,
    val appIcon: Drawable,
    val versionCode: String,
    val versionName: String
)