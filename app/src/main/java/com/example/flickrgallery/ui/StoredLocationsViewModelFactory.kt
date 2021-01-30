package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repo.StoredLocationsRepo


@Suppress("UNCHECKED_CAST")
class StoredLocationsViewModelFactory constructor(
    private val storedLocationsRepo: StoredLocationsRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            StoredLocationsViewModel(storedLocationsRepo) as T
}

