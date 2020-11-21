package com.example.flickrgallery.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FlickrApiClient {

    private const val BASE_URL = "https://www.flickr.com/services/rest/"

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val service: FlickrService = retrofit.create(FlickrService::class.java)
}