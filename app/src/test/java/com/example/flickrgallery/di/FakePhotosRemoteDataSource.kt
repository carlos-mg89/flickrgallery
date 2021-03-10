package com.example.flickrgallery.di

import com.example.data.source.PhotosRemoteDataSource
import com.example.domain.Photo
import com.example.testshared.mockedDomainPhotoList

class FakePhotosRemoteDataSource: PhotosRemoteDataSource {

    override suspend fun getPhotosNearby(
        latitude: Double,
        longitude: Double,
        radiusKm: Double,
        maxResults: Int,
        license: String,
        sort: Int,
        media: String
    ): List<Photo> {
        return mockedDomainPhotoList
    }
}