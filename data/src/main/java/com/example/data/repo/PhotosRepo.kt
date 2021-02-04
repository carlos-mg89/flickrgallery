package com.example.data.repo

import com.example.data.source.PhotosLocalDataSource
import com.example.data.source.PhotosRemoteDataSource
import com.example.domain.Photo
import kotlinx.coroutines.flow.Flow

class PhotosRepo(
    private val local: PhotosLocalDataSource,
    private val remote: PhotosRemoteDataSource
){

    suspend fun getPhotosNearby(latitude:Double, longitude: Double): List<Photo> =
            remote.getPhotosNearby(latitude, longitude)

    suspend fun getAllSavedPhotos(): Flow<List<Photo>> = local.getAll()

    suspend fun getSavedPhoto(id: String) = local.get(id)

    suspend fun insertSavedPhoto(photo: Photo) = local.insert(photo)

    suspend fun deleteSavedPhoto(photo: Photo) = local.delete(photo)

}