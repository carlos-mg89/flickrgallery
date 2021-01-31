package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.repo.PhotosRepo
import com.example.flickrgallery.data.source.toDomainPhoto
import com.example.flickrgallery.data.source.toRoomPhoto
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SavedPhotosViewModel(private val photoRepo: PhotosRepo) : ScopedViewModel() {

    private val _savedPhotos = MutableLiveData<List<Photo>>(emptyList())
    val savedPhotos: LiveData<List<Photo>>
        get() = _savedPhotos

    init {
        startCollectingPhotos()
    }
    
    private fun startCollectingPhotos() {
        launch {
            photoRepo.getAllSavedPhotos().collect {domainPhotos->
                _savedPhotos.value = domainPhotos.map { it.toRoomPhoto() }
            }
        }
    }

    fun deleteSavedPhoto(photo: Photo) {
        launch {
            photoRepo.deleteSavedPhoto(photo.toDomainPhoto())
        }
    }
}