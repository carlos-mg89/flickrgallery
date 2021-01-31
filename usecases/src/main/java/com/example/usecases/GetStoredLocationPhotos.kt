package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo
import com.example.domain.StoredLocation

class GetStoredLocationPhotos (private val photosRepo: PhotosRepo) {

    suspend fun invoke(storedLocation: StoredLocation): List<Photo> {
        return photosRepo.getPhotosNearby(storedLocation.latitude, storedLocation.longitude)
    }
}