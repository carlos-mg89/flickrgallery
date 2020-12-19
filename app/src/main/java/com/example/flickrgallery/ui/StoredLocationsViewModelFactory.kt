package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickrgallery.repo.StoredLocationRepo


@Suppress("UNCHECKED_CAST")
class StoredLocationsViewModelFactory constructor(
    private val storedLocationRepo: StoredLocationRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            StoredLocationsViewModel(storedLocationRepo) as T
}

