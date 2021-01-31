package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repo.PhotosRepo
import com.example.data.repo.StoredLocationsRepo


@Suppress("UNCHECKED_CAST")
class ExploreViewModelFactory constructor(
    private val storedLocationRepo: StoredLocationsRepo,
    private val photoRepo: PhotosRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            ExploreViewModel(storedLocationRepo, photoRepo) as T
}

