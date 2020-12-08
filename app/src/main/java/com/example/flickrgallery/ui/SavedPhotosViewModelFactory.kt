package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickrgallery.repo.PhotoRepo


@Suppress("UNCHECKED_CAST")
class SavedPhotosViewModelFactory constructor(
    private val photoRepo: PhotoRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            SavedPhotosViewModel(photoRepo) as T
}

