package com.example.flickrgallery.ui.explore

import EspressoIdlingResource
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Photo
import com.example.flickrgallery.ui.common.PhotosAdapter


@BindingAdapter("items")
fun RecyclerView.setItems(photos: List<Photo>?) {
    (adapter as? PhotosAdapter)?.let {
        it.photos = photos ?: emptyList()
        if (!photos.isNullOrEmpty()) EspressoIdlingResource.decrement()
    }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}
