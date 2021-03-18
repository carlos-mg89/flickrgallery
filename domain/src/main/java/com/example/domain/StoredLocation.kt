package com.example.domain

import java.io.Serializable
import java.util.*

class StoredLocation: Serializable {

    var id: Int = 0

    var savedDate: Date = Calendar.getInstance().time

    val savedDateString: String
        get() = savedDate.toFormattedString()

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var description: String = ""
}