package com.example.flickrgallery.ui.photo_list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flickrgallery.databinding.PhotoListViewBinding
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.ui.PhotosAdapter


class PhotoListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private var binding: PhotoListViewBinding
    lateinit var photoOnClickListener: (Photo) -> Unit
    private val adapter = PhotosAdapter {
        photoOnClickListener(it)
    }
    var locationPhotos: List<Photo> = emptyList()
        set(value) {
            adapter.photos = value
            field = value
        }

    init {
        val inflater = LayoutInflater.from(context)
        binding = PhotoListViewBinding.inflate(inflater, this, true)
        val manager = GridLayoutManager(getContext(), 3)
        binding.recyclerview.layoutManager = manager
        binding.recyclerview.adapter = adapter
    }

    fun swapItems(photos: List<Photo>){
        adapter.photos = photos
    }
}