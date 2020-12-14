package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickrgallery.repo.GpsRepo
import com.example.flickrgallery.repo.PhotoRepo
import com.example.flickrgallery.repo.StoredLocationRepo


@Suppress("UNCHECKED_CAST")
class ExploreViewModelFactory constructor(
    private val gpsRepo: GpsRepo,
    private val storedLocationRepo: StoredLocationRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            ExploreViewModel(gpsRepo, storedLocationRepo) as T
}

