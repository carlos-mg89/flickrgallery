package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.entities.Location
import com.example.data.repo.StoredLocationsRepo
import com.example.domain.StoredLocation
import com.example.flickrgallery.client.FlickrApiClient
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class ExploreViewModel(
    private val storedLocationsRepo: StoredLocationsRepo
) : ScopedViewModel() {

    private val _exploreUiState = MutableLiveData(ExploreUiState())
    val exploreUiState: LiveData<ExploreUiState>
        get() = _exploreUiState

    var location = Location()

    @ExperimentalCoroutinesApi
    fun proceedGettingUpdates() {
        launch {
            if (storedLocationsRepo.areUpdatesDisabled()) {
                storedLocationsRepo.getPositionUpdates().collect(::onNewPositionReceived)
            }
        }
    }

    private suspend fun onNewPositionReceived(location: Location) {
        setUiUpdatesEnabled()
        setUiBusy()
        this.location = location
        val photos = getPhotos(location.latitude, location.longitude)
        setUiPhotosReceived(photos)
    }

    private suspend fun getPhotos(latitude: Double, longitude: Double): List<Photo> {
        val wayPointPhotosResult = FlickrApiClient.service.listPhotosNearLocation(latitude, longitude)
        return wayPointPhotosResult.photos.photo
    }

    fun storeLocation(description: String = "") {
        launch {
            val location = StoredLocation()
            location.latitude = this@ExploreViewModel.location.latitude
            location.longitude = this@ExploreViewModel.location.longitude
            location.description = description
            storedLocationsRepo.insert(location)
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