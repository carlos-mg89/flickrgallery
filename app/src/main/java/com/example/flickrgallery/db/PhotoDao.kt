package com.example.flickrgallery.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flickrgallery.model.Photo

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos_table ORDER BY id DESC")
    fun loadAllPhotos(): LiveData<List<Photo>>

    @Query("SELECT * FROM photos_table WHERE id = :id")
    fun loadPhotoSync(id: Int): Photo

    @Query("SELECT * FROM photos_table  WHERE is_saved = 1 ORDER BY id DESC")
    fun loadAllSavedPhotos(): LiveData<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(photos: List<Photo>)

    @Update
    fun updatePhoto(photo: Photo)

    @Query("DELETE FROM photos_table")
    fun deleteAll()
}
