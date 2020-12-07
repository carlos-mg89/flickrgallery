package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickrgallery.repo.GpsRepo
import com.example.flickrgallery.repo.LocalRepo


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory constructor(
    private val localRepo: LocalRepo,
    private val gpsRepo: GpsRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            MainViewModel(localRepo, gpsRepo) as T
}
