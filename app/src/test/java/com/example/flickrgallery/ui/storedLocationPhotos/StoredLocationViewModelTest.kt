package com.example.flickrgallery.ui.storedLocationPhotos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.Photo
import com.example.domain.StoredLocation
import com.example.usecases.GetStoredLocationPhotos
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoredLocationViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    private val mockedPhotos = listOf(Photo())

    private val mockedStoredLocation = StoredLocation().apply {
        id = 1
        description = "A testable StoredLocation"
    }

    @Mock
    lateinit var getStoredLocationPhotos: GetStoredLocationPhotos

    @Mock
    lateinit var storedLocationStateObserver: Observer<StoredLocationState>

    lateinit var viewModel: StoredLocationViewModel

    private val initialState = StoredLocationState(
        photos = emptyList(),
        isProgressVisible = false,
    )
    private val loadingState = StoredLocationState(
        photos = emptyList(),
        isProgressVisible = true,
    )
    private val finishedState = StoredLocationState(
        photos = mockedPhotos,
        isProgressVisible = false,
    )

    @Before
    fun setUp() {
        viewModel = StoredLocationViewModel(
            getStoredLocationPhotos,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `after loadPhotos is called, the state goes from initial to loading and to finished`() {
        runBlocking {
            whenever(getStoredLocationPhotos.invoke(any())).thenReturn(mockedPhotos)

            viewModel.storedLocationUiState.observeForever(storedLocationStateObserver)
            viewModel.loadPhotos(mockedStoredLocation)

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
        viewModel.storedLocationUiState.observeForever { observedState ->
            assertEquals(initialState, observedState)
        }
    }

    @Test
    fun `loadPhotos shows loading when starts`() {
        runBlocking {
            whenever(getStoredLocationPhotos.invoke(any())).thenReturn(mockedPhotos)

            viewModel.storedLocationUiState.observeForever(storedLocationStateObserver)
            viewModel.loadPhotos(mockedStoredLocation)

            verify(storedLocationStateObserver).onChanged(loadingState)
        }
    }

    @Test
    fun `loadPhotos shows finished ui state when finished`() {
        runBlocking {
            whenever(getStoredLocationPhotos.invoke(any())).thenReturn(mockedPhotos)

            viewModel.storedLocationUiState.observeForever(storedLocationStateObserver)
            viewModel.loadPhotos(mockedStoredLocation)

            verify(storedLocationStateObserver).onChanged(finishedState)
        }
    }
}