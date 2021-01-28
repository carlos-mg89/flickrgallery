package com.example.data.source

import com.example.domain.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosLocalDataSource {

    fun getAll(): Flow<List<Photo>>
    fun get(id: String): Photo?
    suspend fun insert(photo: Photo)
    suspend fun delete(photo: Photo)
}