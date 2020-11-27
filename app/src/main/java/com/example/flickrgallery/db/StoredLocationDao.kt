package com.example.flickrgallery.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flickrgallery.model.StoredLocation

@Dao
interface StoredLocationDao {

    @Query("SELECT * FROM stored_locations ORDER BY id DESC")
    fun findAll(): List<StoredLocation>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(storedLocation: StoredLocation)

    @Update
    fun update(storedLocation: StoredLocation)

    @Delete
    fun delete(storedLocation: StoredLocation)
}
