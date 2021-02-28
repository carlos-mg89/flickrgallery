package com.example.flickrgallery.ui.savedPhotos

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Photo


@BindingAdapter("savedPhotos")
fun RecyclerView.setSavedPhotos(photos: List<Photo>?) {
    (adapter as? SavedPhotosAdapter)?.let {
        it.photos = photos ?: emptyList()
    }
}