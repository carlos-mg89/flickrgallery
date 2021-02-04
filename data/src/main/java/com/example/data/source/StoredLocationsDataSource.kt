package com.example.data.source

import com.example.domain.StoredLocation
import kotlinx.coroutines.flow.Flow

interface StoredLocationsDataSource {

    fun getAll(): Flow<List<StoredLocation>>
    suspend fun insert(storedLocation: StoredLocation)
    suspend fun update(storedLocation: StoredLocation)
    suspend fun delete(storedLocation: StoredLocation)
}