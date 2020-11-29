package com.example.flickrgallery.gps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.flickrgallery.model.GpsSnapshot
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume


class GpsProvider(context: Context) {


    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission")
    suspend fun getActualLocation(): GpsSnapshot = suspendCancellableCoroutine { continuation ->
            val locationTask = fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, null)
            locationTask.addOnSuccessListener {
                val gpsSnapshot = if (it == null) {
                    buildEmptyGpsSnapshot()
                } else {
                    buildGpsSnapshot(it)
                }
                continuation.resume(gpsSnapshot)
            }
    }

    private fun buildGpsSnapshot(location: Location): GpsSnapshot {
        return GpsSnapshot(
            latitude = location.latitude,
            longitude = location.longitude,
            dateCaptured = location.time
        )
    }

    private fun buildEmptyGpsSnapshot(): GpsSnapshot {
        return GpsSnapshot(
            latitude = 0.0,
            longitude = 0.0,
            dateCaptured = Calendar.getInstance().timeInMillis
        )
    }
}