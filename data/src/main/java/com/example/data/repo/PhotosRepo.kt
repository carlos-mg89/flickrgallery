package com.example.data.repo

import com.example.data.source.PhotosLocalDataSource
import com.example.data.source.PhotosRemoteDataSource
import com.example.domain.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PhotosRepo(
    private val local: PhotosLocalDataSource,
    private val remote: PhotosRemoteDataSource
){

    private val _photosNearby: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val photosNearby: StateFlow<List<Photo>> = _photosNearby

    fun updatePhotosForLocation(latitude: Double, longitude: Double) {
        _photosNearby.value = remote.getPhotosNearby(latitude = latitude, longitude = longitude)
    }

    fun getAllSavedPhotos(): Flow<List<Photo>> = local.getAll()

    fun getSavedPhoto(id: String) = local.get(id)

    suspend fun insertSavedPhoto(photo: Photo) = local.insert(photo)

    suspend fun deleteSavedPhoto(photo: Photo) = local.delete(photo)

}