package com.example.usecases

import com.example.data.repo.StoredLocationsRepo
import com.example.domain.StoredLocation
import kotlinx.coroutines.flow.Flow

class GetStoredLocations(private val storedLocationsRepo: StoredLocationsRepo) {

    fun invoke(): Flow<List<StoredLocation>> = storedLocationsRepo.getAll()
}