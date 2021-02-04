package com.example.flickrgallery.data.source

import com.example.data.source.StoredLocationsDataSource
import com.example.domain.StoredLocation as DomainStoredLocation
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.db.StoredLocationDao
import com.example.flickrgallery.model.StoredLocation as FrameworkStoredLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoredLocationsRoomDataSource (database: Db) : StoredLocationsDataSource {

    private var storedLocationDao: StoredLocationDao = database.storedLocationDao()

    override fun getAll(): Flow<List<DomainStoredLocation>> = storedLocationDao.getAllFlow().map {
        it.map { frameworkStoredLocation -> frameworkStoredLocation.toDomainStoredLocation() }
    }

    override suspend fun insert(storedLocation: DomainStoredLocation) {
        storedLocationDao.insert(storedLocation.toFrameworkStoredLocation())
    }

    override suspend fun update(storedLocation: DomainStoredLocation) {
        storedLocationDao.update(storedLocation.toFrameworkStoredLocation())
    }

    override suspend fun delete(storedLocation: DomainStoredLocation) {
        storedLocationDao.delete(storedLocation.toFrameworkStoredLocation())
    }
}

fun FrameworkStoredLocation.toDomainStoredLocation():DomainStoredLocation {
    val frameworkStoredLocation = this
    return DomainStoredLocation().apply {
        this.id = frameworkStoredLocation.id
        this.description = frameworkStoredLocation.description
        this.latitude = frameworkStoredLocation.latitude
        this.longitude = frameworkStoredLocation.longitude
        this.savedDate = frameworkStoredLocation.savedDate
    }
}

fun DomainStoredLocation.toFrameworkStoredLocation():FrameworkStoredLocation {
    val domainStoredLocation = this
    return FrameworkStoredLocation().apply {
        this.id = domainStoredLocation.id
        this.description = domainStoredLocation.description
        this.latitude = domainStoredLocation.latitude
        this.longitude = domainStoredLocation.longitude
        this.savedDate = domainStoredLocation.savedDate
    }
}