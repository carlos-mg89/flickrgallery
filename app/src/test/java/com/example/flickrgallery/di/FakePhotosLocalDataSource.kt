package com.example.flickrgallery.di

import com.example.data.source.PhotosLocalDataSource
import com.example.domain.Photo
import kotlinx.coroutines.flow.Flow

class FakePhotosLocalDataSource: PhotosLocalDataSource {

    override fun getAll(): Flow<List<Photo>> {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: String): Photo? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(photo: Photo) {}

    override suspend fun delete(photo: Photo) {}
}