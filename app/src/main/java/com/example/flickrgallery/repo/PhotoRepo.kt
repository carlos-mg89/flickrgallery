package com.example.flickrgallery.repo

import com.example.flickrgallery.model.Photo

interface PhotoRepo {


    fun loadStoredPhotos(): List<Photo>
    fun loadStoredPosition(id: Int)

    fun insertOnePhoto(photo: Photo)
    fun insertAllPhotos(photos: List<Photo>)

    fun deleteOnePhoto(photo: String)
}