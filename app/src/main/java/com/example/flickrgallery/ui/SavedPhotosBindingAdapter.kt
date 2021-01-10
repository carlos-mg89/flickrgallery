package com.example.flickrgallery.ui
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrgallery.model.Photo

class SavedPhotosBindingAdapter {
    @BindingAdapter("items")
    fun RecyclerView.setItems(photos: List<Photo>?) {
        (adapter as? PhotosAdapter)?.let {
            it.photos = photos ?: emptyList()
        }
    }
}