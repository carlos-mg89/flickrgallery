package com.example.flickrgallery.di

import com.example.data.source.PhotosLocalDataSource
import com.example.domain.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePhotosLocalDataSource: PhotosLocalDataSource {

    var photos: ArrayList<Photo> = arrayListOf()

    override fun getAll(): Flow<List<Photo>> = flowOf(photos)

    override suspend fun get(id: String): Photo? = photos.firstOrNull { it.id == id }

    override suspend fun insert(photo: Photo) {
        photos.add(photo)
    }

    override suspend fun delete(photo: Photo) {
        photos.remove(photo)
    }
}