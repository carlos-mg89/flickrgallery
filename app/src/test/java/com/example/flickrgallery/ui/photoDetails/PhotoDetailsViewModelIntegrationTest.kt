package com.example.flickrgallery.ui.photoDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.source.PhotosLocalDataSource
import com.example.domain.Photo
import com.example.flickrgallery.di.FakePhotosLocalDataSource
import com.example.flickrgallery.di.initMockedDi
import com.example.testshared.mockedPhoto
import com.example.testshared.mockedUnSavedPhoto
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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
class PhotoDetailsViewModelIntegrationTest: AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var favoriteStatusObserver: Observer<Boolean>

    private lateinit var photoDetailsViewModel: PhotoDetailsViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { PhotoDetailsViewModel(get(), get(), get(), get()) }
        }
        initMockedDi(vmModule)
        photoDetailsViewModel = get()
    }

    private fun setFakePhotosLocalDataSourceWith(photos: List<Photo>) {
        val newPhotos: ArrayList<Photo> = arrayListOf()
        photos.forEach {
            newPhotos.add(it)
        }

        val fakePhotosLocalDataSource = get<PhotosLocalDataSource>() as FakePhotosLocalDataSource
        fakePhotosLocalDataSource.photos = newPhotos
    }

    private fun setFakePhotosLocalDataSourceToBeEmpty() {
        val fakePhotosLocalDataSource = get<PhotosLocalDataSource>() as FakePhotosLocalDataSource
        fakePhotosLocalDataSource.photos.clear()
    }

    @Test
    fun `when checkIfPhotoExists is called on a photo that exists, the favoriteStatusObserver returns true`() {
        runBlocking {
            setFakePhotosLocalDataSourceWith(listOf(mockedPhoto))

            photoDetailsViewModel.favoriteStatus.observeForever(favoriteStatusObserver)
            photoDetailsViewModel.checkIfPhotoExists(mockedPhoto)

            verify(favoriteStatusObserver).onChanged(true)
        }
    }

    @Test
    fun `when checkIfPhotoExists is called on a photo that does not exist, the favoriteStatusObserver returns false`() {
        runBlocking {
            setFakePhotosLocalDataSourceWith(listOf())

            photoDetailsViewModel.favoriteStatus.observeForever(favoriteStatusObserver)
            photoDetailsViewModel.checkIfPhotoExists(mockedPhoto)

            verify(favoriteStatusObserver).onChanged(false)
        }
    }

    @Test
    fun `when toggleSaveStatus is called in a photo that is not saved, the fakeStoredLocationsDataSource will have that photo in it`() {
        runBlocking {
            val fakePhotosLocalDataSource = get<PhotosLocalDataSource>() as FakePhotosLocalDataSource
            fakePhotosLocalDataSource.photos.clear()

            photoDetailsViewModel.favoriteStatus.observeForever(favoriteStatusObserver)
            photoDetailsViewModel.toggleSaveStatus(mockedUnSavedPhoto)

            Assert.assertTrue(fakePhotosLocalDataSource.photos.contains(mockedUnSavedPhoto))
        }
    }

    @Test
    fun `when toggleSaveStatus is called in a photo that is not saved, favoriteStatus is true`() {
        runBlocking {
            setFakePhotosLocalDataSourceToBeEmpty()

            photoDetailsViewModel.favoriteStatus.observeForever(favoriteStatusObserver)
            photoDetailsViewModel.toggleSaveStatus(mockedUnSavedPhoto)

            verify(favoriteStatusObserver).onChanged(true)
        }
    }

    @Test
    fun `when toggleSaveStatus is called in a saved photo, the fakeStoredLocationsDataSource will not contain that photo`() {
        runBlocking {
            val fakePhotosLocalDataSource = get<PhotosLocalDataSource>() as FakePhotosLocalDataSource
            fakePhotosLocalDataSource.photos.add(mockedPhoto)

            photoDetailsViewModel.toggleSaveStatus(mockedPhoto)

            Assert.assertFalse(fakePhotosLocalDataSource.photos.contains(mockedUnSavedPhoto))
        }
    }

    @Test
    fun `when toggleSaveStatus is called in a saved photo, favoriteStatus is false`() {
        runBlocking {
            val fakePhotosLocalDataSource = get<PhotosLocalDataSource>() as FakePhotosLocalDataSource
            fakePhotosLocalDataSource.photos.add(mockedPhoto)

            photoDetailsViewModel.favoriteStatus.observeForever(favoriteStatusObserver)
            photoDetailsViewModel.toggleSaveStatus(mockedPhoto)

            verify(favoriteStatusObserver).onChanged(false)
        }
    }
}