package com.example.flickrgallery.gps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.flickrgallery.model.GpsSnapshot
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class GpsProvider(context: Context) {

    companion object {
        private const val ACCEPTABLE_MINIMUM_LOCATION_ACCURACY = 10
        private const val SECONDS_TO_UPDATE_LOCATION = 1 * 1000L
    }

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    @SuppressLint("MissingPermission")
    @ExperimentalCoroutinesApi
    fun  getPositionUpdates(): Flow<GpsSnapshot> = callbackFlow {
        val locationCallback =  object :LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (isLocationAccurateEnough(location)) {
                        offer(buildGpsSnapshot(location))
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(getLocationRequest(), locationCallback, null)
        awaitClose()
    }

    private fun buildGpsSnapshot(location: Location): GpsSnapshot {
        return GpsSnapshot(
            latitude = location.latitude,
            longitude = location.longitude,
            dateCaptured = location.time
        )
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = SECONDS_TO_UPDATE_LOCATION
        }
    }

    private fun isLocationAccurateEnough(location: Location?): Boolean {
        return location != null && location.accuracy < ACCEPTABLE_MINIMUM_LOCATION_ACCURACY
    }
}