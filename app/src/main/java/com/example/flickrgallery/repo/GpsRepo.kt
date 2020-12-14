package com.example.flickrgallery.repo

import com.example.flickrgallery.model.GpsSnapshot
import kotlinx.coroutines.flow.Flow


interface GpsRepo {
    var areUpdatesDisabled: Boolean
    fun getPositionUpdates(): Flow<GpsSnapshot>
}