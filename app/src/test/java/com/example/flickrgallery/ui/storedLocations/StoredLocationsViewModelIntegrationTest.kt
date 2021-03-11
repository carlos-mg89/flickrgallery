package com.example.flickrgallery.ui.storedLocations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.source.StoredLocationsDataSource
import com.example.domain.StoredLocation
import com.example.flickrgallery.di.FakeStoredLocationsDataSource
import com.example.flickrgallery.di.initMockedDi
import com.example.testshared.mockedStoredLocations
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
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
class StoredLocationsViewModelIntegrationTest: AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var storedLocationsObserver: Observer<List<StoredLocation>>

    private lateinit var storedLocationsViewModel: StoredLocationsViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { StoredLocationsViewModel(get(), get(), get()) }
        }
        initMockedDi(vmModule)
        storedLocationsViewModel = get()
        storedLocationsViewModel.storedLocations.observeForever(storedLocationsObserver)
    }

    private fun setFakeStoredLocationsDataSourceWith(storedLocations: List<StoredLocation>) {
        val newStoredLocations: ArrayList<StoredLocation> = arrayListOf()
        storedLocations.forEach {
            newStoredLocations.add(it)
        }

        val fakeStoredLocationsDataSource = get<StoredLocationsDataSource>() as FakeStoredLocationsDataSource
        fakeStoredLocationsDataSource.storedLocations = newStoredLocations
    }

    @Test
    fun `when viewModel startCollectingStoredLocations, the initial StoredLocations has some values`() {
        runBlocking {
            setFakeStoredLocationsDataSourceWith(mockedStoredLocations)

            storedLocationsViewModel.startCollectingStoredLocations()

            verify(storedLocationsObserver).onChanged(mockedStoredLocations)
        }
    }

    @Test
    fun `after deleting stored location, that StoredLocation isn't the the list anymore`() {
        runBlocking {
            setFakeStoredLocationsDataSourceWith(mockedStoredLocations)

            storedLocationsViewModel.startCollectingStoredLocations()
            storedLocationsViewModel.onStoredLocationDeleteClicked(mockedStoredLocations.first())

            val storedLocationsMinusRemovedOne = mockedStoredLocations.minus(mockedStoredLocations.first())
            verify(storedLocationsObserver).onChanged(storedLocationsMinusRemovedOne)
        }
    }
}