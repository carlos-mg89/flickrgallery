package com.example.flickrgallery.ui.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.model.Location
import com.example.domain.Photo
import com.example.flickrgallery.data.source.toRoomPhoto
import com.example.usecases.GetCurrentLocation
import com.example.usecases.GetCurrentLocationPhotos
import com.example.usecases.SaveStoredLocation
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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
    private val photo = Photo()

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
        photos = listOf(photo.toRoomPhoto()),
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
    fun `view model starts with initial ui state`() {
        viewModel.exploreUiState.observeForever {observedState ->
            assertEquals(initialState, observedState)
        }
    }

    @Test
    fun `proceedGettingUpdates shows loading when starts`() {

        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(listOf(photo))
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
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(listOf(photo))
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