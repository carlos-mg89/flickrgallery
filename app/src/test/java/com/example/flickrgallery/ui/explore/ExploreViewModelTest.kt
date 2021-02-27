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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
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
    private val photo = Photo()
    lateinit var viewModel: ExploreViewModel
    @Mock
    lateinit var observer: Observer<ExploreUiState>

    @Captor
    private lateinit var captor: ArgumentCaptor<ExploreUiState>

    private val initialState = ExploreUiState(isProgressVisible = false, isFabEnabled = false)
    private val loadingState = ExploreUiState(isProgressVisible = true, isFabEnabled = false)
    private val finishedState = ExploreUiState(photos = listOf(photo.toRoomPhoto()), isProgressVisible = false, isFabEnabled = true)


    @Before
    fun setUp() {
        viewModel = ExploreViewModel(
            getCurrentLocation,
            saveStoredLocation,
            getCurrentLocationPhotos
        )
    }

    @Test
    fun `proceedGettingUpdates con Captor FAIL`() {
        // Se recogen 4 interacciones, pero son la misma: la que debería ser la última
        // El viewmodel pasa correctamente el estado
        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(listOf(photo))
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            viewModel.exploreUiState.observeForever(observer)
            viewModel.proceedGettingUpdates()
            verify(observer, times(4)).onChanged(captor.capture())

            val values = captor.allValues
            assertEquals(initialState, values[0])
            assertEquals(loadingState, values[1])
            assertEquals(finishedState, values[2])
        }
    }

    @Test
    fun `proceedGettingUpdates solo verify FAIL`() {
        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(listOf(photo))
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            viewModel.exploreUiState.observeForever(observer)
            viewModel.proceedGettingUpdates()
            verify(observer, times(4)).onChanged(any())

            verify(observer).onChanged(initialState)
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(finishedState)
        }
    }

    @Test
    fun `proceedGettingUpdates verify in order FAIL`() {
        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(listOf(photo))
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            viewModel.exploreUiState.observeForever(observer)
            viewModel.proceedGettingUpdates()

            val order = inOrder(observer)
            order.verify(observer).onChanged(initialState)
            order.verify(observer).onChanged(loadingState)
            order.verify(observer).onChanged(finishedState)
        }
    }

    @Test
    fun `proceedGettingUpdates pasa`() {

        runBlocking {
            whenever(getCurrentLocationPhotos
                .invoke(any(), any()))
                .thenReturn(listOf(photo))
            whenever(getCurrentLocation
                .invoke())
                .thenReturn(listOf(Location()).asFlow())

            var counter = 0
            viewModel.exploreUiState.observeForever {
                val state = it
                when (counter){
                    0 -> {
                        assertFalse("FAB visible", state.isFabEnabled)
                        assertFalse("Progress oculto", state.isProgressVisible)
                        assertTrue("Fotos no vacias", state.photos.isEmpty())
                    }
                    1 -> {
                        assertFalse("FAB visible", state.isFabEnabled)
                        assertTrue("Progress oculto", state.isProgressVisible)
                        assertTrue("Fotos no vacias", state.photos.isEmpty())
                    }
                    2 -> {
                        assertTrue("FAB visible", state.isFabEnabled)
                        assertFalse("Progress oculto", state.isProgressVisible)
                        assertTrue("Fotos no vacias", state.photos.isNotEmpty())
                    }
                }
                counter ++
            }
            viewModel.proceedGettingUpdates()
        }
    }
}