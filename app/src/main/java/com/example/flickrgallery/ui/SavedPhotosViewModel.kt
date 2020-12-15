package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepo
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class SavedPhotosViewModel(private val photoRepo: PhotoRepo) : ScopedViewModel() {

    val savedPhotos: LiveData<List<Photo>>
        get() = photoRepo.getAllLiveData()

    fun deleteSavedPhoto(photo: Photo) {
        launch {
            photoRepo.delete(photo)
        }
    }
}
