package com.example.usecases

import com.example.data.repo.StoredLocationsRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi

class GetCurrentLocation(private val storedLocationsRepo: StoredLocationsRepo) {

    @ExperimentalCoroutinesApi
    fun invoke() = storedLocationsRepo.getPositionUpdates()
}