package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.usecases.DeleteStoredLocation
import com.example.usecases.GetStoredLocations


@Suppress("UNCHECKED_CAST")
class StoredLocationsViewModelFactory constructor(
    private val getStoredLocations: GetStoredLocations,
    private val deleteStoredLocation: DeleteStoredLocation
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            StoredLocationsViewModel(getStoredLocations, deleteStoredLocation) as T
}

