package com.example.flickrgallery.repo

import androidx.lifecycle.LiveData
import com.example.flickrgallery.model.Photo

interface PhotoRepo {

    fun findAllAsLiveData(): LiveData<List<Photo>>
    fun loadStoredPhotos(): List<Photo>
    fun loadStoredPosition(id: Int)

    fun insertAllPhotos(photos: List<Photo>)
    fun delete(photo: Photo)
}