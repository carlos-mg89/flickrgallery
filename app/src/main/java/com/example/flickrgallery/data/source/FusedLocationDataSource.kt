package com.example.flickrgallery.data.source

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location as FrameworkLocation
import com.example.data.model.Location as DataLocation
import com.example.data.source.LocationDataSource
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FusedLocationDataSource(context: Context) : LocationDataSource {

    companion object {
        const val ACCEPTABLE_MINIMUM_LOCATION_ACCURACY = 25
        private const val SECONDS_TO_UPDATE_LOCATION = 1 * 1000L
    }

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    @ExperimentalCoroutinesApi
    override fun getPositionUpdates(): Flow<DataLocation> = callbackFlow {
        val locationCallback =  object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location.isAccurateEnough()) {
                        offer(location.toDataLocation())
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(getLocationRequest(), locationCallback, null)
        awaitClose()
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = SECONDS_TO_UPDATE_LOCATION
        }
    }
}

fun FrameworkLocation.toDataLocation(): DataLocation {
    return DataLocation(
        latitude = this.latitude,
        longitude = this.longitude,
        dateCaptured = this.time
    )
}

fun FrameworkLocation.isAccurateEnough(): Boolean {
    return accuracy < FusedLocationDataSource.ACCEPTABLE_MINIMUM_LOCATION_ACCURACY
}