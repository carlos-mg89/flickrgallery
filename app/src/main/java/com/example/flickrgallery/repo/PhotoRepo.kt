package com.example.flickrgallery.repo

import androidx.lifecycle.LiveData
import com.example.flickrgallery.model.Photo

interface PhotoRepo {

    fun getAllLiveData(): LiveData<List<Photo>>
    fun getAll(): List<Photo>
    fun get(id: Int): Photo

    fun insertAll(photos: List<Photo>)
    fun delete(photo: Photo)
}