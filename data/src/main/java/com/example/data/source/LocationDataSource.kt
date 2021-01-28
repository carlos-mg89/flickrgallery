package com.example.data.source

import com.example.domain.StoredLocation
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {

    var areUpdatesDisabled: Boolean
    fun getPositionUpdates(): Flow<StoredLocation>
}