package com.example.flickrgallery.ui.storedLocationPhotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.Photo
import com.example.domain.StoredLocation
import com.example.flickrgallery.ui.common.ScopedViewModel
import com.example.usecases.GetStoredLocationPhotos
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch


class StoredLocationViewModel(
        private val getStoredLocationPhotos: GetStoredLocationPhotos,
        uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _storedLocationUiState = MutableLiveData(StoredLocationState())
    val storedLocationUiState: LiveData<StoredLocationState>
        get() = _storedLocationUiState

    fun loadPhotos(storedLocation: StoredLocation) {
        setUiBusy()
        launch {
            val photos = getPhotos(storedLocation)
            setUiPhotosReceivedForStoredLocation(photos)
        }
    }

    private suspend fun getPhotos(storedLocation: StoredLocation): List<Photo> {
        return getStoredLocationPhotos.invoke(storedLocation)
    }

    private fun setUiBusy() {
        _storedLocationUiState.value = _storedLocationUiState.value!!.copy(
                isProgressVisible = true,
        ).copy()
    }

    private fun setUiPhotosReceivedForStoredLocation(newPhotos: List<Photo>) {
        _storedLocationUiState.value = StoredLocationState(
            isProgressVisible = false,
            photos = newPhotos
        )
    }
}

data class StoredLocationState(
    var photos: List<Photo> = emptyList(),
    var isProgressVisible: Boolean = false,
)