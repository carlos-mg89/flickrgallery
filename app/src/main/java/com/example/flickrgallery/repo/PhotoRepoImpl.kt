package com.example.flickrgallery.repo

import androidx.lifecycle.LiveData
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.db.PhotoDao
import com.example.flickrgallery.model.Photo

class PhotoRepoImpl (database: Db) : PhotoRepo {

    private var photoDao: PhotoDao = database.photoDao()

    override fun getAllLiveData(): LiveData<List<Photo>> {
        return photoDao.getAllLiveData()
    }

    override suspend fun getAll(): List<Photo> {
        return photoDao.getAll()
    }

    override suspend fun get(id: String): Photo? {
        return photoDao.get(id)
    }

    override suspend fun insertAll(photos: List<Photo>) {
        photoDao.insertAll(photos)
    }

    override suspend fun insert(photo: Photo){
        photoDao.insert(photo)
    }

    override suspend fun delete(photo: Photo) {
        photoDao.delete(photo)
    }
}