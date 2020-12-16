package com.example.flickrgallery.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flickrgallery.model.Photo

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos_table ORDER BY id DESC")
    fun getAllLiveData(): LiveData<List<Photo>>

    @Query("SELECT * FROM photos_table")
    suspend fun getAll(): List<Photo>

    @Query("SELECT * FROM photos_table WHERE id = :id")
    suspend fun get(id: Int): Photo

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(photos: List<Photo>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(photo: Photo)
    @Update
    suspend fun update(photo: Photo)

    @Delete
    suspend fun delete(photo: Photo)

    @Query("DELETE FROM photos_table")
    suspend fun deleteAll()
}
