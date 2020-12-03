package com.example.flickrgallery.repo

import com.example.flickrgallery.model.GpsSnapshot


interface GpsRepo {
    suspend fun getActualPosition(): GpsSnapshot
    fun setAccurateLocationListener(onLocationUpdated: (GpsSnapshot) -> Unit)
}