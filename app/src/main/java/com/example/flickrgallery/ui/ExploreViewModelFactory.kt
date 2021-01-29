package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repo.StoredLocationsRepo


@Suppress("UNCHECKED_CAST")
class ExploreViewModelFactory constructor(
    private val storedLocationRepo: StoredLocationsRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            ExploreViewModel(storedLocationRepo) as T
}

