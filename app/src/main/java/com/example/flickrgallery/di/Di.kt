package com.example.flickrgallery.di

import com.example.data.repo.PhotosRepo
import com.example.data.repo.StoredLocationsRepo
import com.example.data.source.LocationDataSource
import com.example.data.source.PhotosLocalDataSource
import com.example.data.source.PhotosRemoteDataSource
import com.example.data.source.StoredLocationsDataSource
import com.example.flickrgallery.data.source.FusedLocationDataSource
import com.example.flickrgallery.data.source.PhotosFlickerDataSource
import com.example.flickrgallery.data.source.PhotosRoomDataSource
import com.example.flickrgallery.data.source.StoredLocationsRoomDataSource
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.ui.MainActivity
import com.example.usecases.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single { Db.getDatabase(get()) }
}

val dataSourceModule = module {
    single<LocationDataSource> { FusedLocationDataSource(get()) }
    single<StoredLocationsDataSource> { StoredLocationsRoomDataSource(get()) }
    single<PhotosLocalDataSource> { PhotosRoomDataSource(get()) }
    single<PhotosRemoteDataSource> { PhotosFlickerDataSource() }
}

val repoModule = module {
    single { PhotosRepo(get(), get()) }
    single { StoredLocationsRepo(get(), get()) }
}

@ExperimentalCoroutinesApi
val scopedModule = module {
    viewModel
    scope(named<MainActivity>()) {

    }
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
