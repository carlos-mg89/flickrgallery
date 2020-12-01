package com.example.flickrgallery.repo

import com.example.flickrgallery.gps.GpsProvider
import com.example.flickrgallery.model.GpsSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GpsRepoImpl(private val gpsProvider: GpsProvider) : GpsRepo {


    override suspend fun getActualPosition(): GpsSnapshot {
        return withContext(Dispatchers.IO) {
            gpsProvider.getActualLocation()
        }
    }
}