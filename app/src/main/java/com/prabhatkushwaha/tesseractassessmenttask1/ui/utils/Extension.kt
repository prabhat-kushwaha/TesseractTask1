package com.prabhatkushwaha.tesseractassessmenttask1.ui.utils

import android.app.Activity
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView

object Extension {
    fun AppCompatActivity.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}