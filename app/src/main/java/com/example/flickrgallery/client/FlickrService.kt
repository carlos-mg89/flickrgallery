package com.example.flickrgallery.client

import com.example.flickrgallery.model.PhotoDbResult
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {

    companion object {
        private const val FLICKR_API_KEY = "6bb8590ab71ce31cc11ce1b48cf4162a"
        private const val PHOTOS_SEARCH_ENDPOINT = "flickr.photos.search"
        private const val MEDIA_TYPE = "photos"
        private const val DEFAULT_RADIUS_KILOMETERS = 1.0
        private const val DEFAULT_MAX_RESULTS = 10
        private const val LICENSES_ALLOWED_FOR_COMMERCIAL_USE = "4,5,6,9,10"

        private const val ORDER_BY_DATE_POSTED_DESC = 0
        private const val ORDER_BY_DATE_POSTED_ASC = 1
        private const val ORDER_BY_DATE_TAKEN_DESC = 2
        private const val ORDER_BY_DATE_TAKEN_ASC = 3
        private const val ORDER_BY_INTERESTINGNESS_DESC = 4
        private const val ORDER_BY_INTERESTINGNESS_ASC = 5
        private const val ORDER_BY_RELEVANCE = 6
    }

    @GET("?method=$PHOTOS_SEARCH_ENDPOINT&format=json&nojsoncallback=1&api_key=$FLICKR_API_KEY")
    suspend fun listPhotosNearLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("radius") radiusKm: Double = DEFAULT_RADIUS_KILOMETERS,
        @Query("per_page") maxResults: Int = DEFAULT_MAX_RESULTS,
        @Query("license") license: String = LICENSES_ALLOWED_FOR_COMMERCIAL_USE,
        @Query("sort") sort: Int = ORDER_BY_RELEVANCE,
        @Query("media") media: String = MEDIA_TYPE
    ): PhotoDbResult
}