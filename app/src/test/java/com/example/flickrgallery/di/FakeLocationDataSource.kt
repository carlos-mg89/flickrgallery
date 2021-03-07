package com.example.flickrgallery.di

import com.example.data.model.Location
import com.example.data.source.LocationDataSource
import com.example.testshared.mockedLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLocationDataSource: LocationDataSource {

    override fun getPositionUpdates(): Flow<Location> = flowOf(mockedLocation)
}