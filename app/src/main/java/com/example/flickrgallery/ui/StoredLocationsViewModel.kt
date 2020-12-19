package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.StoredLocationRepo
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class StoredLocationsViewModel(private val storedLocationRepo: StoredLocationRepo) : ScopedViewModel() {

    val storedLocations: LiveData<List<StoredLocation>>
        get() = storedLocationRepo.getAllLiveData()

    fun delete(storedLocation: StoredLocation) {
        launch {
            storedLocationRepo.delete(storedLocation)
        }
    }
}
