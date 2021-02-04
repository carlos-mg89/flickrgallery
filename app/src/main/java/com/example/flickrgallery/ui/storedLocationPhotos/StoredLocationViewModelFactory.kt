package com.example.flickrgallery.ui.storedLocationPhotos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usecases.GetStoredLocationPhotos


@Suppress("UNCHECKED_CAST")
class StoredLocationViewModelFactory constructor(
    private val getStoredLocationPhotos: GetStoredLocationPhotos
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            StoredLocationViewModel(getStoredLocationPhotos) as T
}

