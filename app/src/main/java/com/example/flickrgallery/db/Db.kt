package com.example.flickrgallery.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flickrgallery.model.Photo


@TypeConverters(DbConverters::class)
@Database(entities = arrayOf(Photo::class), version = 1)
abstract class Db : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}