package com.example.flickrgallery.repo

import androidx.lifecycle.LiveData
import com.example.flickrgallery.model.StoredLocation

interface StoredLocationRepo {
    fun getAllLiveData(): LiveData<List<StoredLocation>>
    suspend fun getAll(): List<StoredLocation>
    suspend fun insert(storedLocation: StoredLocation)
    suspend fun update(storedLocation: StoredLocation)
    suspend fun delete(storedLocation: StoredLocation)
}