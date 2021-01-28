package com.example.data.source

import com.example.domain.StoredLocation
import kotlinx.coroutines.flow.Flow

interface StoredLocationsDataSource {

    fun getAll(): Flow<List<StoredLocation>>
    fun insert(storedLocation: StoredLocation)
    fun delete(storedLocation: StoredLocation)
}