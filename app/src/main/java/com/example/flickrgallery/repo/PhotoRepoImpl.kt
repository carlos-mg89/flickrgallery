package com.example.flickrgallery.repo

import androidx.lifecycle.LiveData
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo

class PhotoRepoImpl (private val database: Db) : PhotoRepo {

    override fun findAllAsLiveData(): LiveData<List<Photo>> {
        return database.photoDao().loadAllPhotos()
    }

    override fun loadStoredPhotos(): List<Photo> {
        TODO("Not yet implemented")
    }

    override fun loadStoredPosition(id: Int) {
        TODO("Not yet implemented")
    }

    override fun insertAllPhotos(photos: List<Photo>) {
        database.photoDao().insertAll(photos)
    }

    override fun delete(photo: Photo) {
        database.photoDao().delete(photo)
    }
}