package com.example.flickrgallery.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.model.Location
import com.example.domain.Photo
import com.example.domain.StoredLocation
import com.example.flickrgallery.ui.common.ScopedViewModelWithCustomDispatcher
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
) : ScopedViewModelWithCustomDispatcher(uiDispatcher) {

    private val _exploreUiState = MutableLiveData<UiState>(UiState.Initial)
    val exploreUiState: LiveData<UiState>
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
        setUiLoading()
        this.location = location
        val photos = getCurrentLocationPhotos.invoke(location.latitude, location.longitude)
        setPhotosReceivedState(photos)
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

    private fun setUiLoading() = launch {
        _exploreUiState.value = UiState.Loading
    }

    private fun setPhotosReceivedState(photos: List<Photo>) = launch {
        _exploreUiState.value = UiState.Finished(photos)
    }

    sealed class UiState (val photos: List<Photo>) {
        object Initial : UiState(emptyList())
        object Loading : UiState(emptyList())
        class Finished(photos: List<Photo>) : UiState(photos)

        fun isProgressBarVisible() = this is Loading
    }
}
