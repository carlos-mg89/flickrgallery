package com.example.flickrgallery.repo

import androidx.lifecycle.LiveData
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.StoredLocation

class StoredLocationRepoImpl (private val database: Db) : StoredLocationRepo {

    private val storedLocationDao = database.storedLocationDao()

    override fun getAllLiveData(): LiveData<List<StoredLocation>> {
        return storedLocationDao.getAllLiveData()
    }

    override suspend fun getAll(): List<StoredLocation> {
        return storedLocationDao.getAll()
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