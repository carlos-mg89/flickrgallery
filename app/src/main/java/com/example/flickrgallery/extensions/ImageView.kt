package com.example.flickrgallery.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


fun ImageView.loadUrl(url: String?) {
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    url?.let {
        loadUrl(url)
    }
}