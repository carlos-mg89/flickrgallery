package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usecases.DeleteSavedPhoto
import com.example.usecases.GetSavedPhotos


@Suppress("UNCHECKED_CAST")
class SavedPhotosViewModelFactory(
    private val getSavedPhotos: GetSavedPhotos,
    private val deleteSavedPhoto: DeleteSavedPhoto
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            SavedPhotosViewModel(getSavedPhotos, deleteSavedPhoto) as T
}

