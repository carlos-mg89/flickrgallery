package com.example.flickrgallery.di

import com.example.data.source.StoredLocationsDataSource
import com.example.domain.StoredLocation
import com.example.testshared.mockedStoredLocationList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeStoredLocationsDataSource: StoredLocationsDataSource {

    override fun getAll(): Flow<List<StoredLocation>> = flowOf(mockedStoredLocationList)

    override suspend fun insert(storedLocation: StoredLocation) {}

    override suspend fun update(storedLocation: StoredLocation) {}

    override suspend fun delete(storedLocation: StoredLocation) {}
}