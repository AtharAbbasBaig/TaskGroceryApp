package com.baig.taskgrocery.utils

import android.view.View
import android.widget.ImageView
import com.baig.taskgrocery.R
import com.bumptech.glide.Glide

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.image_placeholder)
        .into(this)
}