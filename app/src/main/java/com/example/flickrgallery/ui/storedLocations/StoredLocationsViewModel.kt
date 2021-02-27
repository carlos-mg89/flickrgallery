package com.example.flickrgallery.ui.storedLocations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.StoredLocation
import com.example.flickrgallery.ui.common.Event
import com.example.flickrgallery.ui.common.ScopedViewModelWithCustomDispatcher
import com.example.usecases.DeleteStoredLocation
import com.example.usecases.GetStoredLocations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class StoredLocationsViewModel(
        private val getStoredLocations: GetStoredLocations,
        private val deleteStoredLocation: DeleteStoredLocation,
        uiDispatcher: CoroutineDispatcher
) : ScopedViewModelWithCustomDispatcher(uiDispatcher) {

    private val _storedLocations = MutableLiveData<List<StoredLocation>>(emptyList())
    val storedLocations: LiveData<List<StoredLocation>>
        get() = _storedLocations

    private val _navigateToStoredLocation = MutableLiveData<Event<StoredLocation>>()
    val navigateToStoredLocation : LiveData<Event<StoredLocation>>
        get() = _navigateToStoredLocation

    fun startCollectingStoredLocations() {
        launch {
            getStoredLocations.invoke().collect {
                _storedLocations.value = it
            }
        }
    }

    fun onStoredLocationClicked(storedLocation: StoredLocation){
        _navigateToStoredLocation.value = Event(storedLocation)
    }

    fun onStoredLocationDeleteClicked(storedLocation: StoredLocation) {
        launch {
            deleteStoredLocation.invoke(storedLocation)
        }
    }
}