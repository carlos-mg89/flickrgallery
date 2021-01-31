package com.example.data.source

import com.example.domain.Photo

interface PhotosRemoteDataSource {

    suspend fun getPhotosNearby(
        latitude: Double,
        longitude: Double,
        radiusKm: Double = DEFAULT_RADIUS_KILOMETERS,
        maxResults: Int = DEFAULT_MAX_RESULTS,
        license: String = LICENSES_ALLOWED_FOR_COMMERCIAL_USE,
        sort: Int = ORDER_BY_RELEVANCE,
        media: String = MEDIA_TYPE
    ): List<Photo>


    companion object {

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
}