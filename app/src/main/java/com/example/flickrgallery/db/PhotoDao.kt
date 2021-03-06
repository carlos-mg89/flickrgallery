package com.example.flickrgallery.db

import androidx.room.*
import com.example.flickrgallery.model.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos_table ORDER BY id DESC")
    fun getAllFlow(): Flow<List<Photo>>

    @Query("SELECT * FROM photos_table WHERE id = :id")
    suspend fun get(id: String): Photo?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(photo: Photo)

    @Update
    suspend fun update(photo: Photo)

    @Delete
    suspend fun delete(photo: Photo)
}
