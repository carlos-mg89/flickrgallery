package com.example.flickrgallery.ui.storedLocationPhotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flickrgallery.data.source.toDomainStoredLocation
import com.example.flickrgallery.data.source.toRoomPhoto
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.ui.common.ScopedViewModel
import com.example.usecases.GetStoredLocationPhotos
import kotlinx.coroutines.launch


class StoredLocationViewModel(
        private val getStoredLocationPhotos: GetStoredLocationPhotos
) : ScopedViewModel() {

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
        val photos = getStoredLocationPhotos.invoke(storedLocation.toDomainStoredLocation())
        return photos.map { it.toRoomPhoto() }
    }

    private fun setUiBusy() {
        updateUiState {
            it.isProgressVisible = true
            return@updateUiState it
        }
    }

    private fun setUiPhotosReceivedForStoredLocation(photos: List<Photo>) {
        updateUiState {
            it.isProgressVisible = false
            it.photos = photos
            return@updateUiState it
        }
    }

    private fun updateUiState(updateUi: (StoredLocationState) -> StoredLocationState) {
        val newState = updateUi(_storedLocationUiState.value!!)
        _storedLocationUiState.postValue(newState)
    }
}

data class StoredLocationState(
    var photos: List<Photo> = emptyList(),
    var isProgressVisible: Boolean = false,
)