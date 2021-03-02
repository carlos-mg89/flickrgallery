package com.example.flickrgallery.ui.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.model.Location
import com.example.domain.Photo
import com.example.flickrgallery.data.source.toRoomPhoto
import com.example.usecases.GetCurrentLocation
import com.example.usecases.GetCurrentLocationPhotos
import com.example.usecases.SaveStoredLocation
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ExploreViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getCurrentLocation: GetCurrentLocation
    @Mock
    lateinit var saveStoredLocation: SaveStoredLocation
    @Mock
    lateinit var getCurrentLocationPhotos: GetCurrentLocationPhotos
    private val mockedDomainPhotos = listOf(Photo())
    private val mockedFrameworkPhotos = listOf(Photo().toRoomPhoto())

    @Captor
    private lateinit var captor: ArgumentCaptor<ExploreUiState>
    @Mock
    lateinit var observer: Observer<ExploreUiState>

    lateinit var viewModel: ExploreViewModel

    private val initialState = ExploreUiState(
        photos = emptyList(),
        isProgressVisible = false,
        isFabEnabled = false
    )
    private val loadingState = ExploreUiState(
        photos = emptyList(),
        isProgressVisible = true,
        isFabEnabled = false
    )
    private val finishedState = ExploreUiState(
        photos = mockedFrameworkPhotos,
        isProgressVisible = false,
        isFabEnabled = true
    )

    @Before
    fun setUp() {
        viewModel = ExploreViewModel(
            getCurrentLocation,
            saveStoredLocation,
            getCurrentLocationPhotos,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `proceedGettingUpdates con Captor FAIL`() {
        // Se recogen 4 interacciones, pero son la misma: la que debería ser la última
        // El viewmodel pasa correctamente el estado
        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(mockedDomainPhotos)
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            viewModel.exploreUiState.observeForever(observer)
            viewModel.proceedGettingUpdates()
            verify(observer, times(3)).onChanged(captor.capture())

            val values = captor.allValues
            assertEquals(initialState, values[0])
            assertEquals(loadingState, values[1])
            assertEquals(finishedState, values[2])
        }
    }

    @Test
    fun `proceedGettingUpdates solo verify FAIL`() {
        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(mockedDomainPhotos)
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            viewModel.exploreUiState.observeForever(observer)
            viewModel.proceedGettingUpdates()
            verify(observer, times(3)).onChanged(any())

            verify(observer).onChanged(initialState)
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(finishedState)
        }
    }

    @Test
    fun `proceedGettingUpdates verify in order FAIL`() {
        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(mockedDomainPhotos)
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            viewModel.exploreUiState.observeForever(observer)
            viewModel.proceedGettingUpdates()

            observer.inOrder {
                verify(observer).onChanged(initialState)
                verify(observer).onChanged(loadingState)
                verify(observer).onChanged(finishedState)
            }
        }
    }

    @Test
    fun `view model starts with initial ui state`() {
        viewModel.exploreUiState.observeForever {observedState ->
            assertEquals(initialState, observedState)
        }
    }

    @Test
    fun `proceedGettingUpdates shows loading when starts`() {

        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(mockedDomainPhotos)
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            var receivedStatePosition = 1
            viewModel.exploreUiState.observeForever {observedState ->
                if (receivedStatePosition == 2) assertEquals(loadingState, observedState)
                receivedStatePosition ++
            }
            viewModel.proceedGettingUpdates()
        }
    }

    @Test
    fun `proceedGettingUpdates shows finished ui state when finished`() {

        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(mockedDomainPhotos)
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            var receivedStatePosition = 1
            viewModel.exploreUiState.observeForever { observedState ->
                if (receivedStatePosition == 3) {
                    assertEquals(finishedState, observedState)
                }
            }
            receivedStatePosition++
        }
        viewModel.proceedGettingUpdates()
    }

    @Test
    fun `onSaveStoreLocationClick calls use case`() {
        runBlocking {
            viewModel.storeLocation()
            verify(saveStoredLocation).invoke(any())
        }
    }
}