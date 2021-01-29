package com.example.flickrgallery.data.source

import com.example.data.source.StoredLocationsDataSource
import com.example.domain.StoredLocation
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.db.StoredLocationDao
import kotlinx.coroutines.flow.Flow

class StoredLocationsRoomDataSource (database: Db) : StoredLocationsDataSource {

    private var storedLocationDao: StoredLocationDao = database.storedLocationDao()

    override suspend fun getAll(): Flow<List<StoredLocation>> {
        return storedLocationDao.getAllFlow()
    }

    override suspend fun insert(storedLocation: StoredLocation) {
        storedLocationDao.insert(storedLocation)
    }

    override suspend fun update(storedLocation: StoredLocation) {
        storedLocationDao.update(storedLocation)
    }

    override suspend fun delete(storedLocation: StoredLocation) {
        storedLocationDao.delete(storedLocation)
    }
}