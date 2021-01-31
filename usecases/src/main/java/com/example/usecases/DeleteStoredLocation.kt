package com.example.usecases

import com.example.data.repo.StoredLocationsRepo
import com.example.domain.StoredLocation

class DeleteStoredLocation(private val storedLocationsRepo: StoredLocationsRepo) {

    suspend fun invoke(storedLocation: StoredLocation) {
        storedLocationsRepo.delete(storedLocation)
    }
}