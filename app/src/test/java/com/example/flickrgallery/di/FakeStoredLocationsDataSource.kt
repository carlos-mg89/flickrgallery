package com.example.flickrgallery.di

import com.example.data.source.StoredLocationsDataSource
import com.example.domain.StoredLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeStoredLocationsDataSource: StoredLocationsDataSource {

    var storedLocations: ArrayList<StoredLocation> = arrayListOf()

    override fun getAll(): Flow<List<StoredLocation>> = flowOf(storedLocations)

    override suspend fun insert(storedLocation: StoredLocation) {
        storedLocations.plus(storedLocation)
    }

    override suspend fun update(storedLocation: StoredLocation) {
        var storedLocationIndex = -1
        storedLocations.forEachIndexed { index, it ->
            if (it.id == storedLocation.id) storedLocationIndex = index
        }

        if (storedLocationIndex >= 0) {
            storedLocations[storedLocationIndex] = storedLocation
        } else {
            insert(storedLocation)
        }
    }

    override suspend fun delete(storedLocation: StoredLocation) {
        storedLocations.remove(storedLocation)
    }
}