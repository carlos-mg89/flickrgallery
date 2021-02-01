package com.example.data.source

import com.example.data.entities.Location
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {

    fun getPositionUpdates(): Flow<Location>
}