package com.example.flickrgallery.repo

import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo

class LocalRepoImpl (private val database: Db) : LocalRepo {

    override fun loadStoredPhotos(): List<Photo> {
        TODO("Not yet implemented")
    }

    override fun loadStoredPosition(id: Int) {
        TODO("Not yet implemented")
    }


    override fun insertAllPhotos(photos: List<Photo>) {
        database.photoDao().insertAll(photos)
    }
}