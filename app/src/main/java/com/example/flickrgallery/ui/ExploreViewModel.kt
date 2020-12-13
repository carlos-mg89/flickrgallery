package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrgallery.client.FlickrApiClient
import com.example.flickrgallery.model.GpsSnapshot
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.GpsRepo
import com.example.flickrgallery.repo.StoredLocationRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val gpsRepo: GpsRepo,
    private val storedLocationRepo: StoredLocationRepo
) : ViewModel() {

    private val _exploreUiState = MutableLiveData(ExploreUiState())
    val exploreUiState: LiveData<ExploreUiState>
        get() = _exploreUiState

    var gpsSnapshot = GpsSnapshot()

    fun proceedGettingUpdates() {
        viewModelScope.launch(Dispatchers.Main) {
            if (gpsRepo.areUpdatesDisabled) {
                gpsRepo.getPositionUpdates().collect { position ->
                    updateUiState {
                        it.isFabEnabled = true
                        return@updateUiState it
                    }
                    val currentGpsSnapshot = GpsSnapshot(
                        latitude = position.latitude,
                        longitude = position.longitude,
                        dateCaptured = position.dateCaptured
                    )
                    gpsSnapshot = currentGpsSnapshot
                    postPhotosAt(gpsSnapshot.latitude, gpsSnapshot.longitude)
                }
            }
        }
    }

    private suspend fun postPhotosAt(latitude: Double, longitude: Double) {
        setUiBusy()
        val photos = getPhotos(latitude, longitude)
        updateUiState {
            it.photos = photos
            it.isProgressVisible = false
            it.isFabEnabled = true
            return@updateUiState it
        }
    }

    private suspend fun getPhotos(latitude: Double, longitude: Double): List<Photo> {
        val wayPointPhotosResult = FlickrApiClient.service.listPhotosNearLocation(latitude, longitude)
        return wayPointPhotosResult.photos.photo
    }

    fun storeLocation(description: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            val location = StoredLocation()
            location.latitude = gpsSnapshot.latitude
            location.longitude = gpsSnapshot.longitude
            location.description = description
            storedLocationRepo.insert(location)
        }
    }

    private fun setUiBusy(){
        updateUiState {
            it.isProgressVisible = true
            it.isFabEnabled = false
            return@updateUiState it
        }
    }

    private fun updateUiState(updateUi: (ExploreUiState) -> ExploreUiState) {
        val newState = updateUi(_exploreUiState.value!!)
        _exploreUiState.postValue(newState)
    }
}

data class ExploreUiState(
    var photos: List<Photo> = emptyList(),
    var isProgressVisible: Boolean = false,
    var isFabEnabled: Boolean = false
)