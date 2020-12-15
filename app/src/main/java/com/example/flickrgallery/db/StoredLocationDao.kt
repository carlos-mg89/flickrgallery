package com.example.flickrgallery.db

import androidx.room.*
import com.example.flickrgallery.model.StoredLocation

@Dao
interface StoredLocationDao {

    @Query("SELECT * FROM stored_locations ORDER BY id DESC")
    suspend fun findAll(): List<StoredLocation>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(storedLocation: StoredLocation)

    @Update
    suspend fun update(storedLocation: StoredLocation)

    @Delete
    suspend fun delete(storedLocation: StoredLocation)
}
