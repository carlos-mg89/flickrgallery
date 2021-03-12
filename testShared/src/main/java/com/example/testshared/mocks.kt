package com.example.testshared

import com.example.data.model.Location
import com.example.domain.Photo
import com.example.domain.StoredLocation
import java.util.*

val mockedDomainPhoto = Photo().apply {
    this.id = "1"
    this.isSaved = false
}
val mockedDomainPhotoList = listOf(mockedDomainPhoto)

val mockedStoredLocation = StoredLocation()
val mockedStoredLocationList = listOf(mockedStoredLocation)

val mockedLocation = Location()

val mockedStoredLocations = listOf(
    StoredLocation().apply {
        id = 2
        savedDate = Calendar.getInstance().time
        latitude = 42.10
        longitude = 2.35
        description = "Location #2"
    },
    StoredLocation().apply {
        id = 1
        savedDate = Calendar.getInstance().time
        latitude = 41.10
        longitude = 1.35
        description = "Location #1"
    }
)

val mockedPhoto = Photo().apply {
    id = "1"
}

val mockedUnSavedPhoto = Photo().apply {
    id = "1"
    isSaved = false
}

val mockedStoredLocation1 = StoredLocation().apply {
    id = 1
    description = "A testable StoredLocation"
}

val mockedPhotos = listOf(
        Photo().apply {
            id = "1"
            title = "Test Photo 1"
        },
        Photo().apply {
            id = "2"
            title = "Test Photo 2"
        }
)