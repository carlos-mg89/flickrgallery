package com.example.flickrgallery.repo

import com.example.flickrgallery.model.Photo

interface PhotoRepo {


    fun loadStoredPhotos(): List<Photo>
    fun loadStoredPosition(id: Int)

    fun insertAllPhotos(photos: List<Photo>)
}