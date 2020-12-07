package com.example.flickrgallery.repo

import com.example.flickrgallery.model.GpsSnapshot


interface GpsRepo {
    var areUpdatesDisabled: Boolean
    fun setAccurateLocationListener(onLocationUpdated: (GpsSnapshot) -> Unit)
}