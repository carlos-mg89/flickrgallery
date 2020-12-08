package com.example.flickrgallery.repo

import androidx.lifecycle.LiveData
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.db.PhotoDao
import com.example.flickrgallery.model.Photo

class PhotoRepoImpl (database: Db) : PhotoRepo {

    private var photoDao: PhotoDao = database.photoDao()

    override fun findAllAsLiveData(): LiveData<List<Photo>> {
        return photoDao.loadAllPhotos()
    }

    override fun loadStoredPhotos(): List<Photo> {
        TODO("Not yet implemented")
    }

    override fun loadStoredPosition(id: Int) {
        TODO("Not yet implemented")
    }

    override fun insertAllPhotos(photos: List<Photo>) {
        photoDao.insertAll(photos)
    }

    override fun delete(photo: Photo) {
        photoDao.delete(photo)
    }
}