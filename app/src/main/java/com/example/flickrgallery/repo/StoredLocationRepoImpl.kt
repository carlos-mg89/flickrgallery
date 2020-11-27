package com.example.flickrgallery.repo

import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.StoredLocation

class StoredLocationRepoImpl (private val database: Db) : StoredLocationRepo {

    override fun findAll(): List<StoredLocation> {
        return database.storedLocationDao().findAll()
    }

    override fun insert(storedLocation: StoredLocation) {
        database.storedLocationDao().insert(storedLocation)
    }

    override fun update(storedLocation: StoredLocation) {
        database.storedLocationDao().update(storedLocation)
    }

    override fun delete(storedLocation: StoredLocation) {
        database.storedLocationDao().delete(storedLocation)
    }


}