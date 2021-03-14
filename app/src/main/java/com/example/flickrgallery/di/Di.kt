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
import com.example.flickrgallery.ui.explore.ExploreFragment
import com.example.flickrgallery.ui.explore.ExploreViewModel
import com.example.flickrgallery.ui.photoDetails.PhotoDetailsFragment
import com.example.flickrgallery.ui.photoDetails.PhotoDetailsViewModel
import com.example.flickrgallery.ui.savedPhotos.SavedPhotosFragment
import com.example.flickrgallery.ui.savedPhotos.SavedPhotosViewModel
import com.example.flickrgallery.ui.storedLocationPhotos.StoredLocationFragment
import com.example.flickrgallery.ui.storedLocationPhotos.StoredLocationViewModel
import com.example.flickrgallery.ui.storedLocations.StoredLocationsFragment
import com.example.flickrgallery.ui.storedLocations.StoredLocationsViewModel
import com.example.usecases.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val appModule = module {
    single { Db.getDatabase(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
}

val dataSourceModule = module {
    single<LocationDataSource> { FusedLocationDataSource(get()) }
    single<StoredLocationsDataSource> { StoredLocationsRoomDataSource(get()) }
    single<PhotosLocalDataSource> { PhotosRoomDataSource(get()) }
    single(named("baseUrl")) { "https://www.flickr.com/services/rest/" }
    single<PhotosRemoteDataSource> { PhotosFlickerDataSource(get(named("baseUrl"))) }
}

val repoModule = module {
    single { PhotosRepo(get(), get()) }
    single { StoredLocationsRepo(get(), get()) }
}

@ExperimentalCoroutinesApi
val scopedModule = module {
    scope(named<MainActivity>()) {  }
    scope(named<ExploreFragment>()) {
        viewModel { ExploreViewModel(get(), get(), get(), get()) }
    }
    scope(named<PhotoDetailsFragment>()) {
        viewModel { PhotoDetailsViewModel(get(), get(), get(), get()) }
    }
    scope(named<SavedPhotosFragment>()) {
        viewModel { SavedPhotosViewModel(get(), get(), get()) }
    }
    scope(named<StoredLocationFragment>()) {
        viewModel { StoredLocationViewModel(get(), get()) }
    }
    scope(named<StoredLocationsFragment>()) {
        viewModel { StoredLocationsViewModel(get(), get(), get()) }
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
