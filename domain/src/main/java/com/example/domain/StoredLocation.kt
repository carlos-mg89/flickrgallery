package com.example.domain

import java.text.DateFormat
import java.util.*

class StoredLocation {

    var id: Int = 0

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