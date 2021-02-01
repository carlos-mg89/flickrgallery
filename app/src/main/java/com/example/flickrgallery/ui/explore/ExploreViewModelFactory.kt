package com.example.flickrgallery.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usecases.GetCurrentLocation
import com.example.usecases.GetCurrentLocationPhotos
import com.example.usecases.SaveStoredLocation


@Suppress("UNCHECKED_CAST")
class ExploreViewModelFactory constructor(
    private val getCurrentLocation: GetCurrentLocation,
    private val saveStoredLocation: SaveStoredLocation,
    private val getCurrentLocationPhotos: GetCurrentLocationPhotos
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            ExploreViewModel(getCurrentLocation, saveStoredLocation, getCurrentLocationPhotos) as T
}

