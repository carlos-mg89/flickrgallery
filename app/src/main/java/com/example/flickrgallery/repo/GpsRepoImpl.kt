package com.example.flickrgallery.repo

import com.example.flickrgallery.gps.GpsProvider
import com.example.flickrgallery.model.GpsSnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow


class GpsRepoImpl(private val gpsProvider: GpsProvider) : GpsRepo {

    override var areUpdatesDisabled = true

    @ExperimentalCoroutinesApi
    override fun getPositionUpdates(): Flow<GpsSnapshot> {
        areUpdatesDisabled = false
        return gpsProvider.getPositionUpdates()
    }
}