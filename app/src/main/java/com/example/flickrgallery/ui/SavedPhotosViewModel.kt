package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepo

class SavedPhotosViewModel(val photoRepo: PhotoRepo) : ViewModel() {

    val savedPhotos: LiveData<List<Photo>>
        get() = photoRepo.findAllAsLiveData()

    fun deleteSavedPhoto(photo: Photo) {
        photoRepo.delete(photo)
    }
}
