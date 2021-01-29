package com.example.domain

import java.util.*

class StoredLocation {

    var id: Int = 0

    var savedDate: Date = Calendar.getInstance().time

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var description: String = ""
}