package com.example.flickrgallery.repo

import com.example.flickrgallery.model.StoredLocation

interface StoredLocationRepo {
    suspend fun findAll(): List<StoredLocation>
    suspend fun insert(storedLocation: StoredLocation)
    suspend fun update(storedLocation: StoredLocation)
    suspend fun delete(storedLocation: StoredLocation)
}