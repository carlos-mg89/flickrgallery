package com.example.flickrgallery.data.source

import com.example.data.source.PhotosRemoteDataSource
import com.example.flickrgallery.client.FlickrService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.domain.Photo as DomainPhoto

class PhotosFlickerDataSource (baseUrl: String) : PhotosRemoteDataSource {

    val okHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    private val service = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run { create(FlickrService::class.java) }

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