package com.example.flickrgallery.repo

import com.example.flickrgallery.gps.GpsProvider
import com.example.flickrgallery.model.GpsSnapshot


class GpsRepoImpl(private val gpsProvider: GpsProvider) : GpsRepo {

    override var areUpdatesDisabled = true

    override fun setAccurateLocationListener(onLocationUpdated: (GpsSnapshot) -> Unit) {
        gpsProvider.setAccurateLocationListener {
            onLocationUpdated(it)
        }
    }
}