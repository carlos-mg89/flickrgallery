package com.example.flickrgallery.data.source

import com.example.data.source.PhotosRemoteDataSource
import com.example.flickrgallery.client.FlickrService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.domain.Photo as DomainPhoto

class PhotosFlickerDataSource: PhotosRemoteDataSource{

    companion object{
        private const val BASE_URL = "https://www.flickr.com/services/rest/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: FlickrService = retrofit.create(FlickrService::class.java)

    override suspend fun getPhotosNearby(
        latitude: Double,
        longitude: Double,
        radiusKm: Double,
        maxResults: Int,
        license: String,
        sort: Int,
        media: String
    ): List<DomainPhoto> {
        val photos = service.listPhotosNearLocation(latitude, longitude, radiusKm, maxResults, license, sort, media).photos.photo
        return photos.map { it.toDomainPhoto() }
    }

}