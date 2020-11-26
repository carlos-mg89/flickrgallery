package com.example.flickrgallery.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flickrgallery.model.Photo


@TypeConverters(DbConverters::class)
@Database(entities = [Photo::class], version = 1)
abstract class Db : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object {
        private var INSTANCE: Db? = null
        private const val DATABASE_NAME = "location-scout.db"

        fun getDatabase(context: Context): Db {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.applicationContext, Db::class.java, DATABASE_NAME
                ).build()
            }
            return INSTANCE!!
        }
    }
}