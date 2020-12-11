package com.example.flickrgallery.repo

import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo

class PhotoRepoImpl (private val database: Db) : PhotoRepo {

    override fun loadStoredPhotos(): List<Photo> {
        TODO("Not yet implemented")
    }

    override fun loadStoredPosition(id: Int) {
        TODO("Not yet implemented")
    }


    override fun insertAllPhotos(photos: List<Photo>) {
        database.photoDao().insertAll(photos)
    }

    override fun insertOnePhoto(photo: Photo){
        database.photoDao().insertOnePhoto(photo)
    }

    override fun deleteOnePhoto(id: String) {
        database.photoDao().deleteOnePhoto(id)
    }
}