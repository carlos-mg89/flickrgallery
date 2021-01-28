package com.example.data.repo

import com.example.data.source.LocationDataSource
import com.example.data.source.StoredLocationsDataSource
import com.example.domain.StoredLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class StoredLocationsRepo(
    private val storedLocationsDataSource: StoredLocationsDataSource,
    private val locationDataSource: LocationDataSource
) {
    fun getAll(): Flow<List<StoredLocation>> {
        return storedLocationsDataSource.getAll()
    }

    suspend fun insert(storedLocation: StoredLocation) =
        storedLocationsDataSource.insert(storedLocation)

    suspend fun delete(storedLocation: StoredLocation) {
        storedLocationsDataSource.delete(storedLocation)
    }

    @ExperimentalCoroutinesApi
    fun getPositionUpdates(): Flow<StoredLocation> {
        return locationDataSource.getPositionUpdates()
    }

    fun setLocationUpdatesEnabled(enabled: Boolean) {
        locationDataSource.areUpdatesDisabled = enabled
    }
}