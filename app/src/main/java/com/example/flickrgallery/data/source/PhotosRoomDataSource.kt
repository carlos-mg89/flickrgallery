package com.example.flickrgallery.data.source

import com.example.data.source.PhotosLocalDataSource
import com.example.flickrgallery.db.Db
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.domain.Photo as DomainPhoto
import com.example.flickrgallery.model.Photo as RoomPhoto

class PhotosRoomDataSource(database: Db): PhotosLocalDataSource {

    private val photoDao = database.photoDao()

    override fun getAll(): Flow<List<DomainPhoto>> = photoDao.getAllFlow().map {
        it.map { frameworkPhoto -> frameworkPhoto.toDomainPhoto() }
    }

    override suspend fun get(id: String): DomainPhoto? {
        return photoDao.get(id)?.toDomainPhoto()
    }

    override suspend fun insert(photo: DomainPhoto) {
        photoDao.insert(photo.toRoomPhoto())
    }

    override suspend fun delete(photo: DomainPhoto) {
        photoDao.delete(photo.toRoomPhoto())
    }


}

fun DomainPhoto.toRoomPhoto(): RoomPhoto{
    val frameworkPhoto = this
    return RoomPhoto().apply {
        this.id = frameworkPhoto.id
        this.farm= frameworkPhoto.farm
        this.family= frameworkPhoto.family
        this.friend= frameworkPhoto.friend
        this.publicLicense= frameworkPhoto.publicLicense
        this.owner= frameworkPhoto.owner
        this.secret= frameworkPhoto.secret
        this.server= frameworkPhoto.server
        this.title= frameworkPhoto.title
        this.latitude= frameworkPhoto.latitude
        this.longitude= frameworkPhoto.longitude
        this.observation= frameworkPhoto.observation
        this.isSaved= frameworkPhoto.isSaved
        this.savedDate= frameworkPhoto.savedDate
    }
}

fun RoomPhoto.toDomainPhoto(): DomainPhoto{
    val domainPhoto = this
    return DomainPhoto().apply {
        this.id = domainPhoto.id
        this.farm= domainPhoto.farm
        this.family= domainPhoto.family
        this.friend= domainPhoto.friend
        this.publicLicense= domainPhoto.publicLicense
        this.owner= domainPhoto.owner
        this.secret= domainPhoto.secret
        this.server= domainPhoto.server
        this.title= domainPhoto.title
        this.latitude= domainPhoto.latitude
        this.longitude= domainPhoto.longitude
        this.observation= domainPhoto.observation
        this.isSaved= domainPhoto.isSaved
        this.savedDate= domainPhoto.savedDate
    }
}