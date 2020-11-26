package com.example.flickrgallery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "stored_locations")
class StoredLocation {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "saved_date")
    var savedDate: Date = Calendar.getInstance().time

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var description: String = ""
}