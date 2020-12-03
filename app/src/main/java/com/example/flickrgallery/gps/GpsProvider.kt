package com.example.flickrgallery.gps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.flickrgallery.model.GpsSnapshot
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import kotlin.coroutines.resume


class GpsProvider(context: Context) {

    companion object {
        private const val ACCEPTABLE_MINIMUM_LOCATION_ACCURACY = 10
        private const val SECONDS_TO_UPDATE_LOCATION = 5 * 1000L
    }

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

    @SuppressLint("MissingPermission")
    fun setAccurateLocationListener(onLocationUpdated: (GpsSnapshot) -> Unit) {
        fusedLocationClient.requestLocationUpdates(
                getLocationRequest(), getLocationCallback(onLocationUpdated), null
        )
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = SECONDS_TO_UPDATE_LOCATION
        }
    }

    private fun getLocationCallback(onLocationUpdated: (GpsSnapshot) -> Unit) = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (isLocationAccurateEnough(location)) {
                    onLocationUpdated(buildGpsSnapshot(location))
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }
    }

    private fun isLocationAccurateEnough(location: Location?): Boolean {
        return location != null && location.accuracy < ACCEPTABLE_MINIMUM_LOCATION_ACCURACY
    }
}