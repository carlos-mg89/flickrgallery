package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flickrgallery.client.FlickrApiClient
import com.example.flickrgallery.model.GpsSnapshot
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.GpsRepo
import com.example.flickrgallery.repo.StoredLocationRepo
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val gpsRepo: GpsRepo,
    private val storedLocationRepo: StoredLocationRepo
) : ScopedViewModel() {

    private val _exploreUiState = MutableLiveData(ExploreUiState())
    val exploreUiState: LiveData<ExploreUiState>
        get() = _exploreUiState

    var gpsSnapshot = GpsSnapshot()

    fun proceedGettingUpdates() {
        launch {
            if (gpsRepo.areUpdatesDisabled) {
                gpsRepo.getPositionUpdates().collect(::onNewPositionReceived)
            }
        }
    }

    fun loadPhotos(storedLocation: StoredLocation) {
        setUiBusy()
        val storedLocationGpsSnapshot = getGpsSnapshot(storedLocation)
        launch {
            val photos = getPhotos(storedLocationGpsSnapshot.latitude, storedLocationGpsSnapshot.longitude)
            setUiPhotosReceivedForStoredLocation(photos)
        }
    }

    private fun getGpsSnapshot(storedLocation: StoredLocation): GpsSnapshot {
        return GpsSnapshot(
                storedLocation.longitude,
                storedLocation.latitude,
                storedLocation.savedDate.time
        )
    }

    private suspend fun onNewPositionReceived(gpsSnapshot: GpsSnapshot) {
        setUiUpdatesEnabled()
        setUiBusy()
        this.gpsSnapshot = gpsSnapshot
        val photos = getPhotos(gpsSnapshot.latitude, gpsSnapshot.longitude)
        setUiPhotosReceived(photos)
    }

    private suspend fun getPhotos(latitude: Double, longitude: Double): List<Photo> {
        val wayPointPhotosResult = FlickrApiClient.service.listPhotosNearLocation(latitude, longitude)
        return wayPointPhotosResult.photos.photo
    }

    fun storeLocation(description: String = "") {
        launch {
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

    private fun setUiUpdatesEnabled(){
        updateUiState {
            it.isFabEnabled = true
            return@updateUiState it
        }
    }

    private fun setUiPhotosReceived(photos: List<Photo>){
        updateUiState {
            it.isProgressVisible = false
            it.isFabEnabled = true
            it.photos = photos
            return@updateUiState it
        }
    }

    private fun setUiPhotosReceivedForStoredLocation(photos: List<Photo>) {
        updateUiState {
            it.isProgressVisible = false
            it.isFabEnabled = false
            it.photos = photos
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