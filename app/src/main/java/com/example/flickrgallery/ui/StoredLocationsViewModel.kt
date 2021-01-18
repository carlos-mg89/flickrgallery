package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.StoredLocationRepo
import com.example.flickrgallery.ui.common.Event
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class StoredLocationsViewModel(private val storedLocationRepo: StoredLocationRepo) : ScopedViewModel() {

    val storedLocations = storedLocationRepo.getAllLiveData()

    private val _navigateToStoredLocation = MutableLiveData<Event<StoredLocation>>()
    val navigateToStoredLocation : LiveData<Event<StoredLocation>>
        get() = _navigateToStoredLocation


    fun onStoredLocationClicked(storedLocation: StoredLocation){
        _navigateToStoredLocation.value = Event(storedLocation)
    }

    fun onStoredLocationDeleteClicked(storedLocation: StoredLocation) {
        launch { storedLocationRepo.delete(storedLocation) }
    }
}