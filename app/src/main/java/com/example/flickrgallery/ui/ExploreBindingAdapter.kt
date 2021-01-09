package com.example.flickrgallery.ui

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrgallery.model.Photo



@BindingAdapter("items")
fun RecyclerView.setItems(photos: List<Photo>?) {
    (adapter as? PhotosAdapter)?.let {
        it.photos = photos ?: emptyList()
    }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}
