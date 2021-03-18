package com.example.flickrgallery.ui.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.flickrgallery.di.initMockedDi
import com.example.testshared.mockedDomainPhoto
import com.nhaarman.mockitokotlin2.lastValue
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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
class ExploreViewModelIntegrationTest: AutoCloseKoinTest() {

    @Mock
    lateinit var stateObserver: Observer<ExploreUiState>

    @get: Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var exploreViewModel: ExploreViewModel

    @Before
    fun setup() {
        val vmModule = module {
            factory { ExploreViewModel(get(), get(), get(), get()) }
        }
        initMockedDi(vmModule)
        exploreViewModel = get()
    }

    @Test
    fun `on proceed getting updates, the ui state is filled with a photo`() {
        runBlocking {
            val expectedPhotoId = mockedDomainPhoto.id

            exploreViewModel.exploreUiState.observeForever(stateObserver)
            exploreViewModel.proceedGettingUpdates()

            val captor: ArgumentCaptor<ExploreUiState> = ArgumentCaptor.forClass(ExploreUiState::class.java)
            verify(stateObserver, times(2)).onChanged(captor.capture())

            val actualPhotoId = captor.lastValue.photos.last().id
            Assert.assertEquals(expectedPhotoId, actualPhotoId)
        }
    }
}