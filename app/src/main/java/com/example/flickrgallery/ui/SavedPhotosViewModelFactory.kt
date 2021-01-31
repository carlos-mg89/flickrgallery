package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repo.PhotosRepo


@Suppress("UNCHECKED_CAST")
class SavedPhotosViewModelFactory(
    private val photoRepo: PhotosRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            SavedPhotosViewModel(photoRepo) as T
}

