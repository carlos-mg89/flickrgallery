package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo

class GetSelectedPhoto(private val photosRepo: PhotosRepo) {

    suspend fun invoke(id: String): Photo?{
        return photosRepo.getSavedPhoto(id)
    }
}