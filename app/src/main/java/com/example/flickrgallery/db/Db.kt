package com.example.flickrgallery.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.model.StoredLocation


@TypeConverters(DbConverters::class)
@Database(entities = [Photo::class, StoredLocation::class], version = 1)
abstract class Db : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
    abstract fun storedLocationDao(): StoredLocationDao

    companion object {

        private const val DATABASE_NAME = "location-scout.db"

        fun getDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            Db::class.java, DATABASE_NAME
        ).build()
    }
}