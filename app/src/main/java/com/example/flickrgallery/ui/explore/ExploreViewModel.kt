package com.example.flickrgallery.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.model.Location
import com.example.domain.StoredLocation
import com.example.flickrgallery.data.source.toRoomPhoto
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.ui.common.ScopedViewModel
import com.example.usecases.GetCurrentLocation
import com.example.usecases.GetCurrentLocationPhotos
import com.example.usecases.SaveStoredLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ExploreViewModel(
        private val getCurrentLocation: GetCurrentLocation,
        private val saveStoredLocation: SaveStoredLocation,
        private val getCurrentLocationPhotos: GetCurrentLocationPhotos
) : ScopedViewModel() {

    private val _exploreUiState = MutableLiveData(ExploreUiState())
    val exploreUiState: LiveData<ExploreUiState>
        get() = _exploreUiState

    var location = Location()

    @ExperimentalCoroutinesApi
    fun proceedGettingUpdates() {
        launch {
            getCurrentLocation.invoke().collect {
                onNewPositionReceived(it)
            }
        }
    }

    private suspend fun onNewPositionReceived(location: Location) {
        setUiUpdatesEnabled()
        setUiBusy()
        this.location = location
        val photos = getCurrentLocationPhotos.invoke(location.latitude, location.longitude)
        setUiPhotosReceived(photos.map { it.toRoomPhoto() })
    }

    fun storeLocation(description: String = "") {
        launch {
            val location = StoredLocation()
            location.latitude = this@ExploreViewModel.location.latitude
            location.longitude = this@ExploreViewModel.location.longitude
            location.description = description
            saveStoredLocation.invoke(location)
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
        _exploreUiState.value = newState
    }
}

data class ExploreUiState(
    var photos: List<Photo> = emptyList(),
    var isProgressVisible: Boolean = false,
    var isFabEnabled: Boolean = false
)