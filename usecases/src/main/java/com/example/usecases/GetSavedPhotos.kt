package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo
import kotlinx.coroutines.flow.Flow

class GetSavedPhotos(private val photosRepo: PhotosRepo) {

    fun invoke(): Flow<List<Photo>> = photosRepo.getAllSavedPhotos()

}