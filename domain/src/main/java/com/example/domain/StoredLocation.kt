package com.example.domain

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class StoredLocation: Serializable {

    var id: Int = 0

    var savedDate: Date = Calendar.getInstance().time

    val savedDateString: String
        get() = stringFromSavedDate()

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var description: String = ""

    private fun stringFromSavedDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yy H:mm")
        return dateFormat.format(savedDate)
    }
}