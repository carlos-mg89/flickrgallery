package com.example.flickrgallery.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.model.Location
import com.example.domain.Photo
import com.example.domain.StoredLocation
import com.example.flickrgallery.ui.common.ScopedViewModel
import com.example.usecases.GetCurrentLocation
import com.example.usecases.GetCurrentLocationPhotos
import com.example.usecases.SaveStoredLocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ExploreViewModel(
        private val getCurrentLocation: GetCurrentLocation,
        private val saveStoredLocation: SaveStoredLocation,
        private val getCurrentLocationPhotos: GetCurrentLocationPhotos,
        uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _exploreUiState = MutableLiveData(ExploreUiState())
    val exploreUiState: LiveData<ExploreUiState>
        get() = _exploreUiState

    var location = Location()

    init {
        setUiBusy()
    }

    @ExperimentalCoroutinesApi
    fun proceedGettingUpdates() {
        launch {
            getCurrentLocation.invoke().collect {
                onNewPositionReceived(it)
            }
        }
    }

    private suspend fun onNewPositionReceived(location: Location) {
        this.location = location
        val photos = getCurrentLocationPhotos.invoke(location.latitude, location.longitude)
        setUiPhotosReceived(photos)
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

    private fun setUiBusy() {
        _exploreUiState.value = ExploreUiState(
                isProgressVisible = true,
                isFabEnabled = false,
        )
    }

    private fun setUiPhotosReceived(photos: List<Photo>){
        _exploreUiState.value = ExploreUiState(
                isProgressVisible = false,
                isFabEnabled = true,
                photos = photos
        )
    }
}

data class ExploreUiState(
    var photos: List<Photo> = emptyList(),
    var isProgressVisible: Boolean = false,
    var isFabEnabled: Boolean = false
)