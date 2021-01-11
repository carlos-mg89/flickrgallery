package com.example.flickrgallery.ui

import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepo
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class SavedPhotosViewModel(private val photoRepo: PhotoRepo) : ScopedViewModel() {

    val savedPhotos= photoRepo.getAllLiveData()

    fun deleteSavedPhoto(photo: Photo) {
        launch {
            photoRepo.delete(photo)
        }
    }
}