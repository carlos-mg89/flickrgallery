package com.example.flickrgallery.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.domain.StoredLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface StoredLocationDao {

    @Query("SELECT * FROM stored_locations ORDER BY id DESC")
    fun getAllLiveData(): LiveData<List<StoredLocation>>

    @Query("SELECT * FROM stored_locations ORDER BY id DESC")
    fun getAllFlow(): Flow<List<StoredLocation>>

    @Query("SELECT * FROM stored_locations ORDER BY id DESC")
    suspend fun getAll(): List<StoredLocation>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(storedLocation: StoredLocation)

    @Update
    suspend fun update(storedLocation: StoredLocation)

    @Delete
    suspend fun delete(storedLocation: StoredLocation)
}
