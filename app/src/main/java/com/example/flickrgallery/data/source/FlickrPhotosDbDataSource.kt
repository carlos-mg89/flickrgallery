package com.example.flickrgallery.data.source

import com.example.data.source.PhotosRemoteDataSource
import com.example.domain.Photo

class FlickrPhotosDbDataSource (private val photosFlickerDb: PhotosFlickerDb) : PhotosRemoteDataSource {

    override suspend fun getPhotosNearby(
        latitude: Double,
        longitude: Double,
        radiusKm: Double,
        maxResults: Int,
        license: String,
        sort: Int,
        media: String
    ): List<Photo> {
        val photos = photosFlickerDb.service.listPhotosNearLocation(
            latitude, longitude, radiusKm, maxResults, license, sort, media
        ).photos.photo

        return photos.map { it.toDomainPhoto() }
    }
}