package com.example.flickrgallery.ui.savedPhotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.Photo
import com.example.flickrgallery.ui.common.ScopedViewModelWithCustomDispatcher
import com.example.usecases.DeleteSavedPhoto
import com.example.usecases.GetSavedPhotos
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SavedPhotosViewModel(
    private val getSavedPhotos: GetSavedPhotos,
    private val deleteSavedPhoto: DeleteSavedPhoto,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModelWithCustomDispatcher(uiDispatcher) {

    private val _savedPhotos = MutableLiveData<List<Photo>>(emptyList())
    val savedPhotos: LiveData<List<Photo>>
        get() = _savedPhotos

    fun startCollectingPhotos() {
        launch {
            getSavedPhotos.invoke().collect {
                _savedPhotos.value = it
            }
        }
    }

    fun deleteSavedPhoto(photo: Photo) {
        launch {
            deleteSavedPhoto.invoke(photo)
        }
    }
}