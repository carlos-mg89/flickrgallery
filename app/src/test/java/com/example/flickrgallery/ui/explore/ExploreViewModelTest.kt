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

    private val mockedPhotos = listOf(Photo())

    lateinit var viewModel: ExploreViewModel
    @Mock
    lateinit var stateObserver: Observer<ExploreViewModel.UiState>


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
    fun `after proceedGettingUpdates is called, verify that the Finished state happens right after the Loading state`() {
        runBlocking {
            whenever(getCurrentLocationPhotos.invoke(any(), any())).thenReturn(mockedPhotos)
            whenever(getCurrentLocation.invoke()).thenReturn(listOf(Location()).asFlow())

            viewModel.exploreUiState.observeForever(stateObserver)
            viewModel.proceedGettingUpdates()

            verify(stateObserver, times(3)).onChanged(any())
            stateObserver.inOrder {
                verify(stateObserver).onChanged(isA<ExploreViewModel.UiState.Initial>())
                verify(stateObserver).onChanged(isA<ExploreViewModel.UiState.Loading>())
                verify(stateObserver).onChanged(isA<ExploreViewModel.UiState.Finished>())
            }
        }
    }
}