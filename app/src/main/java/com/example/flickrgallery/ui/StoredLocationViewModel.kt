package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flickrgallery.client.FlickrApiClient
import com.example.flickrgallery.model.GpsSnapshot
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.launch


class StoredLocationViewModel: ScopedViewModel() {

    private val _storedLocationUiState = MutableLiveData(StoredLocationState())
    val storedLocationUiState: LiveData<StoredLocationState>
        get() = _storedLocationUiState


    fun loadPhotos(storedLocation: StoredLocation) {
        setUiBusy()
        val storedLocationGpsSnapshot = getGpsSnapshot(storedLocation)
        launch {
            val photos =
                getPhotos(storedLocationGpsSnapshot.latitude, storedLocationGpsSnapshot.longitude)
            setUiPhotosReceivedForStoredLocation(photos)
        }
    }

    private suspend fun getPhotos(latitude: Double, longitude: Double): List<Photo> {
        val wayPointPhotosResult = FlickrApiClient.service.listPhotosNearLocation(latitude, longitude)
        return wayPointPhotosResult.photos.photo
    }

    private fun getGpsSnapshot(storedLocation: StoredLocation): GpsSnapshot {
        return GpsSnapshot(
            storedLocation.longitude,
            storedLocation.latitude,
            storedLocation.savedDate.time
        )
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