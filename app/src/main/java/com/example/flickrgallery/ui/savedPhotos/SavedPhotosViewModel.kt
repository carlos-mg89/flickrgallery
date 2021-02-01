package com.example.flickrgallery.ui.savedPhotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flickrgallery.data.source.toDomainPhoto
import com.example.flickrgallery.data.source.toRoomPhoto
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.ui.common.ScopedViewModel
import com.example.usecases.DeleteSavedPhoto
import com.example.usecases.GetSavedPhotos
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SavedPhotosViewModel(private val getSavedPhotos: GetSavedPhotos,
                           private val deleteSavedPhoto: DeleteSavedPhoto) : ScopedViewModel() {

    private val _savedPhotos = MutableLiveData<List<Photo>>(emptyList())
    val savedPhotos: LiveData<List<Photo>>
        get() = _savedPhotos

    init {
        startCollectingPhotos()
    }
    
    private fun startCollectingPhotos() {
        launch {
            getSavedPhotos.invoke().collect {
                _savedPhotos.value = it.map { photoDomain -> photoDomain.toRoomPhoto() }
            }
        }
    }

    fun deleteSavedPhoto(photoRoom: Photo) {
        launch {
            deleteSavedPhoto.invoke(photoRoom.toDomainPhoto())
        }
    }
}