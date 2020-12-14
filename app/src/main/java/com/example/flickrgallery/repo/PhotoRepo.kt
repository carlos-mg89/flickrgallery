package com.example.flickrgallery.repo

import androidx.lifecycle.LiveData
import com.example.flickrgallery.model.Photo

interface PhotoRepo {

    fun getAllLiveData(): LiveData<List<Photo>>
    suspend fun getAll(): List<Photo>
    suspend fun get(id: Int): Photo

    suspend fun insertAll(photos: List<Photo>)
    suspend fun delete(photo: Photo)
}