package com.example.flickrgallery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.DateFormat
import java.util.*


@Entity(tableName = "stored_locations")
class StoredLocation: Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "saved_date")
    var savedDate: Date = Calendar.getInstance().time

    val savedDateString: String
        get() = stringFromSavedDate()

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var description: String = ""


    private fun stringFromSavedDate(): String {
        val dateFormat = DateFormat.getDateTimeInstance(
            DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault()
        )
        return try { dateFormat.format(savedDate) } catch (e: Exception){ "" }
    }
}