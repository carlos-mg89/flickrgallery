package com.example.flickrgallery.ui.photoDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.Photo
import com.example.usecases.GetSelectedPhoto
import com.example.usecases.MarkPhotoAsFavorite
import com.example.usecases.UnMarkPhotoAsFavorite
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class PhotoDetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getSelectedPhoto: GetSelectedPhoto

    @Mock
    lateinit var markPhotoAsFavorite: MarkPhotoAsFavorite

    @Mock
    lateinit var unMarkPhotoAsFavorite: UnMarkPhotoAsFavorite

    @Mock
    lateinit var favoriteStatusObserver: Observer<Boolean>

    private lateinit var vm: PhotoDetailsViewModel

    private val mockedPhoto = Photo().apply {
        id = "1"
    }

    private val mockedUnSavedPhoto = Photo().apply {
        id = "1"
        isSaved = false
    }

    @Before
    fun setUp() {
        vm = PhotoDetailsViewModel(
            getSelectedPhoto,
            markPhotoAsFavorite,
            unMarkPhotoAsFavorite,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `when checkIfPhotoExists is called, the getSelectedPhoto use case is invoked`() {
        runBlocking {
            vm.checkIfPhotoExists(mockedPhoto)

            verify(getSelectedPhoto).invoke(mockedPhoto.id)
        }
    }

    @Test
    fun `when toggleSaveStatus is called in a photo that is not saved, the markPhotoAsFavorite use case is called`() {
        runBlocking {
            whenever( markPhotoAsFavorite.invoke(mockedUnSavedPhoto) ).thenReturn(Unit)

            vm.favoriteStatus.observeForever(favoriteStatusObserver)
            vm.toggleSaveStatus(mockedUnSavedPhoto)

            verify(favoriteStatusObserver).onChanged(true)
            verify(markPhotoAsFavorite).invoke(mockedUnSavedPhoto)
        }
    }

    @Test
    fun `when toggleSaveStatus is called in a photo that is not saved, favoriteStatus is true`() {
        runBlocking {
            whenever( markPhotoAsFavorite.invoke(mockedUnSavedPhoto) ).thenReturn(Unit)

            vm.favoriteStatus.observeForever(favoriteStatusObserver)
            vm.toggleSaveStatus(mockedUnSavedPhoto)

            verify(favoriteStatusObserver).onChanged(true)
        }
    }

    @Test
    fun `when toggleSaveStatus is called in a saved photo, the unMarkPhotoAsFavorite use case is invoked`() {
        runBlocking {
            whenever( getSelectedPhoto.invoke(mockedPhoto.id) ).thenReturn(mockedPhoto)
            whenever( unMarkPhotoAsFavorite.invoke(any()) ).thenReturn(Unit)

            vm.favoriteStatus.observeForever(favoriteStatusObserver)
            vm.checkIfPhotoExists(mockedPhoto)
            vm.toggleSaveStatus(mockedPhoto)

            verify(unMarkPhotoAsFavorite).invoke(mockedPhoto)
        }
    }

    @Test
    fun `when toggleSaveStatus is called in a saved photo, favoriteStatus is false`() {
        runBlocking {
            whenever( getSelectedPhoto.invoke(mockedPhoto.id) ).thenReturn(mockedPhoto)

            vm.favoriteStatus.observeForever(favoriteStatusObserver)
            vm.checkIfPhotoExists(mockedPhoto)
            vm.toggleSaveStatus(mockedPhoto)

            verify(favoriteStatusObserver, times(2)).onChanged(false)
        }
    }
}