package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo

class GetCurrentLocationPhotos (private val photosRepo: PhotosRepo) {

    suspend fun invoke(latitude: Double, longitude: Double): List<Photo> {
        return photosRepo.getPhotosNearby(latitude, longitude)
    }
}