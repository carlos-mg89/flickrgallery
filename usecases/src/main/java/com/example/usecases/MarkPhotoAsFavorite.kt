package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo

class MarkPhotoAsFavorite(private val photosRepo: PhotosRepo) {

    suspend fun invoke(photo: Photo){
        photosRepo.insertSavedPhoto(photo)
    }
}