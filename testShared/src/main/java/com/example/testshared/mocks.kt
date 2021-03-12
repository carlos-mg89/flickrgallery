package com.example.testshared

import com.example.data.model.Location
import com.example.domain.Photo
import com.example.domain.StoredLocation

val mockedDomainPhoto = Photo().apply {
    this.id = "1"
    this.isSaved = false
}
val mockedDomainPhotoList = listOf(mockedDomainPhoto)

val mockedStoredLocation = StoredLocation()
val mockedStoredLocationList = listOf(mockedStoredLocation)

val mockedLocation = Location()

val mockedPhoto = Photo().apply {
    id = "1"
}

val mockedUnSavedPhoto = Photo().apply {
    id = "1"
    isSaved = false
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