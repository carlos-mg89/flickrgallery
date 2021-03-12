package com.example.flickrgallery.ui.savedPhotos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.source.PhotosLocalDataSource
import com.example.domain.Photo
import com.example.flickrgallery.di.FakePhotosLocalDataSource
import com.example.flickrgallery.di.initMockedDi
import com.example.testshared.mockedPhotos
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SavedPhotosViewModelIntegrationTest: AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var photosSavedObserver: Observer<List<Photo>>

    private lateinit var savedPhotosViewModel: SavedPhotosViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { SavedPhotosViewModel(get(), get(), get()) }
        }
        initMockedDi(vmModule)
        savedPhotosViewModel = get()
    }

    @Test
    fun `when startCollectingPhotos is called, the photosSavedObserver contains mockedPhotos`() {
        runBlocking {
            val fakePhotosLocalDataSource = get<PhotosLocalDataSource>() as FakePhotosLocalDataSource
            fakePhotosLocalDataSource.photos.addAll(mockedPhotos)

            savedPhotosViewModel.savedPhotos.observeForever(photosSavedObserver)
            savedPhotosViewModel.startCollectingPhotos()

            verify(photosSavedObserver).onChanged(mockedPhotos)
        }
    }

    @Test
    fun `when deleteSavedPhoto is called with a photo, the photosSavedObserver will not contain it anymore`() {
        runBlocking {
            val fakePhotosLocalDataSource = get<PhotosLocalDataSource>() as FakePhotosLocalDataSource
            fakePhotosLocalDataSource.photos.addAll(mockedPhotos)

            savedPhotosViewModel.savedPhotos.observeForever(photosSavedObserver)
            savedPhotosViewModel.startCollectingPhotos()
            savedPhotosViewModel.deleteSavedPhoto(mockedPhotos.first())

            verify(photosSavedObserver).onChanged(
                    mockedPhotos.minus(mockedPhotos.first())
            )
        }
    }
}