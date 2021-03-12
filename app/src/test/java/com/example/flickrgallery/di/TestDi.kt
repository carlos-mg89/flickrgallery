package com.example.flickrgallery.di

import com.example.data.repo.PhotosRepo
import com.example.data.repo.StoredLocationsRepo
import com.example.data.source.LocationDataSource
import com.example.data.source.PhotosLocalDataSource
import com.example.data.source.PhotosRemoteDataSource
import com.example.data.source.StoredLocationsDataSource
import com.example.usecases.*
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


val testModule = module {
    single { Dispatchers.Unconfined }

    single<LocationDataSource> { FakeLocationDataSource() }
    single<StoredLocationsDataSource> { FakeStoredLocationsDataSource() }
    single<PhotosLocalDataSource> { FakePhotosLocalDataSource() }
    single<PhotosRemoteDataSource> { FakePhotosRemoteDataSource() }
}

val useCasesModule = module {
    single { DeleteSavedPhoto(get()) }
    single { DeleteStoredLocation(get()) }
    single { GetCurrentLocation(get()) }
    single { GetCurrentLocationPhotos(get()) }
    single { GetSavedPhotos(get()) }
    single { GetSelectedPhoto(get()) }
    single { GetStoredLocationPhotos(get()) }
    single { GetStoredLocations(get()) }
    single { MarkPhotoAsFavorite(get()) }
    single { SaveStoredLocation(get()) }
    single { UnMarkPhotoAsFavorite(get()) }
}

val repoModule = module {
    single { PhotosRepo(get(), get()) }
    single { StoredLocationsRepo(get(), get()) }
}

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules( listOf(testModule, repoModule, useCasesModule) + modules)
    }
}