package com.example.domain

import java.util.*

class Photo {

    var id: String = ""
    var farm: Int = -1
    var family: Int = -1
    var friend: Int = -1
    var publicLicense: Int = -1
    var owner: String = ""
    var secret: String = ""
    var server: String = ""
    var title: String = ""
    var latitude: String = ""
    var longitude: String = ""
    var observation: String = ""
    var isSaved = false
    var savedDate: Date = Calendar.getInstance().time
}
