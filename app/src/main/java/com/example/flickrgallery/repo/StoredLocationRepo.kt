package com.example.flickrgallery.repo

import com.example.flickrgallery.model.StoredLocation

interface StoredLocationRepo {
    fun findAll(): List<StoredLocation>
    fun insert(storedLocation: StoredLocation)
    fun update(storedLocation: StoredLocation)
    fun delete(storedLocation: StoredLocation)
}