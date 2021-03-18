package com.example.flickrgallery.ui.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.model.Location
import com.example.domain.Photo
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
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ExploreViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    private val mockedPhotos = listOf(Photo())

    @Mock
    lateinit var getCurrentLocation: GetCurrentLocation
    @Mock
    lateinit var saveStoredLocation: SaveStoredLocation
    @Mock
    lateinit var getCurrentLocationPhotos: GetCurrentLocationPhotos

    @Mock
    lateinit var stateObserver: Observer<ExploreUiState>

    lateinit var viewModel: ExploreViewModel

    private val loadingState = ExploreUiState(
        photos = emptyList(),
        isProgressVisible = true,
        isFabEnabled = false
    )
    private val finishedState = ExploreUiState(
        photos = mockedPhotos,
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
    fun `after proceedGettingUpdates is called, the state goes from initial to loading and to finished`() {
        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(mockedPhotos)
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            viewModel.exploreUiState.observeForever(stateObserver)
            viewModel.proceedGettingUpdates()

            val captor: ArgumentCaptor<ExploreUiState> = ArgumentCaptor.forClass(ExploreUiState::class.java)
            verify(stateObserver, times(2)).onChanged(captor.capture())

            assertEquals(loadingState.isFabEnabled, captor.firstValue.isFabEnabled)
            assertEquals(loadingState.isProgressVisible, captor.firstValue.isProgressVisible)
            assertEquals(loadingState.photos.size, captor.firstValue.photos.size)

            assertEquals(finishedState.isFabEnabled, captor.secondValue.isFabEnabled)
            assertEquals(finishedState.isProgressVisible, captor.secondValue.isProgressVisible)
            assertEquals(finishedState.photos.size, captor.secondValue.photos.size)
        }
    }

    @Test
    fun `view model starts with initial ui state`() {
        viewModel.exploreUiState.observeForever {observedState ->
            assertEquals(loadingState, observedState)
        }
    }

    @Test
    fun `proceedGettingUpdates shows loading when starts`() {

        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(mockedPhotos)
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            var receivedStatePosition = 1
            viewModel.exploreUiState.observeForever {observedState ->
                if (receivedStatePosition == 2) assertEquals(finishedState, observedState)
                receivedStatePosition ++
            }
            viewModel.proceedGettingUpdates()
        }
    }

    @Test
    fun `proceedGettingUpdates shows finished ui state when finished`() {

        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(mockedPhotos)
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