package com.example.flickrgallery.ui.storedLocationPhotos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.flickrgallery.di.initMockedDi
import com.example.testshared.mockedDomainPhotoList
import com.example.testshared.mockedStoredLocation1
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoredLocationViewModelIntegrationTest: AutoCloseKoinTest() {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var storedLocationStateObserver: Observer<StoredLocationState>

    lateinit var storedLocationViewModel: StoredLocationViewModel

    private val initialState = StoredLocationState(
        photos = emptyList(),
        isProgressVisible = false,
    )
    private val loadingState = StoredLocationState(
        photos = emptyList(),
        isProgressVisible = true,
    )
    private val finishedState = StoredLocationState(
        photos = mockedDomainPhotoList,
        isProgressVisible = false,
    )

    @Before
    fun setUp() {
        val vmModule = module {
            factory { StoredLocationViewModel(get(), get()) }
        }
        initMockedDi(vmModule)
        storedLocationViewModel = get()
    }

    @Test
    fun `after loadPhotos is called, the state goes from initial to loading and to finished`() {
        runBlocking {
            storedLocationViewModel.storedLocationUiState.observeForever(storedLocationStateObserver)
            storedLocationViewModel.loadPhotos(mockedStoredLocation1)

            val captor: ArgumentCaptor<StoredLocationState> = ArgumentCaptor.forClass(StoredLocationState::class.java)
            verify(storedLocationStateObserver, times(3)).onChanged(captor.capture())

            assertEquals(initialState.isProgressVisible, captor.firstValue.isProgressVisible)
            assertEquals(initialState.photos.size, captor.firstValue.photos.size)

            assertEquals(loadingState.isProgressVisible, captor.secondValue.isProgressVisible)
            assertEquals(loadingState.photos.size, captor.secondValue.photos.size)

            assertEquals(finishedState.isProgressVisible, captor.thirdValue.isProgressVisible)
            assertEquals(finishedState.photos.size, captor.thirdValue.photos.size)
        }
    }

    @Test
    fun `view model starts with initial ui state`() {
        storedLocationViewModel.storedLocationUiState.observeForever { observedState ->
            assertEquals(initialState, observedState)
        }
    }

    @Test
    fun `after loadPhotos is called, loadingState will be in storedLocationStateObserver`() {
        runBlocking {
            storedLocationViewModel.storedLocationUiState.observeForever(storedLocationStateObserver)
            storedLocationViewModel.loadPhotos(mockedStoredLocation1)

            verify(storedLocationStateObserver).onChanged(loadingState)
        }
    }

    @Test
    fun `after loadPhotos is called, the finishedState will be at storedLocationStateObserver`() {
        runBlocking {
            storedLocationViewModel.storedLocationUiState.observeForever(storedLocationStateObserver)
            storedLocationViewModel.loadPhotos(mockedStoredLocation1)

            verify(storedLocationStateObserver, times(1)).onChanged(initialState)
            verify(storedLocationStateObserver, times(1)).onChanged(loadingState)
            verify(storedLocationStateObserver, times(1)).onChanged(finishedState)
        }
    }
}