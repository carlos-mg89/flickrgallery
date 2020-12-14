package com.example.flickrgallery.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flickrgallery.model.Photo

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos_table ORDER BY id DESC")
    fun getAllLiveData(): LiveData<List<Photo>>

    @Query("SELECT * FROM photos_table")
    fun getAll(): List<Photo>

    @Query("SELECT * FROM photos_table WHERE id = :id")
    fun get(id: Int): Photo

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(photos: List<Photo>)

    @Update
    fun update(photo: Photo)

    @Delete
    fun delete(photo: Photo)

    @Query("DELETE FROM photos_table")
    fun deleteAll()
}
