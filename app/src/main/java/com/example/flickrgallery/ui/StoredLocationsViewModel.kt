package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.repo.StoredLocationsRepo
import com.example.domain.StoredLocation
import com.example.flickrgallery.ui.common.Event
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StoredLocationsViewModel(
    private val storedLocationsRepo: StoredLocationsRepo
) : ScopedViewModel() {

    private val _storedLocations = MutableLiveData<List<StoredLocation>>(emptyList())
    val storedLocations: LiveData<List<StoredLocation>>
        get() = _storedLocations

    private val _navigateToStoredLocation = MutableLiveData<Event<StoredLocation>>()
    val navigateToStoredLocation : LiveData<Event<StoredLocation>>
        get() = _navigateToStoredLocation

    fun startCollectingStoredLocations() {
        launch {
            storedLocationsRepo.getAll().collect {
                _storedLocations.value = it
            }
        }
    }

    fun onStoredLocationClicked(storedLocation: StoredLocation){
        _navigateToStoredLocation.value = Event(storedLocation)
    }

    fun onStoredLocationDeleteClicked(storedLocation: StoredLocation) {
        launch { storedLocationsRepo.delete(storedLocation) }
    }
}