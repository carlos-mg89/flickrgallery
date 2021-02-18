package com.example.flickrgallery.ui.storedLocations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.StoredLocation
import com.example.usecases.DeleteStoredLocation
import com.example.usecases.GetStoredLocations
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class StoredLocationsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getStoredLocations: GetStoredLocations
    @Mock
    lateinit var deleteStoredLocation: DeleteStoredLocation

    @Mock
    lateinit var storedLocationsObserver: Observer<List<StoredLocation>>

    private lateinit var vm: StoredLocationsViewModel

    private val mockedStoredLocations = listOf(
        StoredLocation().apply {
            id = 2
            savedDate = Calendar.getInstance().time
            latitude = 42.10
            longitude = 2.35
            description = "Location #2"
        },
        StoredLocation().apply {
            id = 1
            savedDate = Calendar.getInstance().time
            latitude = 41.10
            longitude = 1.35
            description = "Location #1"
        }
    )

    @Before
    fun setUp() {
        vm = StoredLocationsViewModel(
            getStoredLocations,
            deleteStoredLocation,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `after deleting stored location, the deleteStoredLocation use case is invoked`() {
        val storedLocations = mockedStoredLocations

        runBlocking {
            whenever( deleteStoredLocation.invoke(storedLocations.first()) ).thenReturn(Unit)

            vm.onStoredLocationDeleteClicked(storedLocations.first())

            verify(deleteStoredLocation).invoke(storedLocations.first())
        }
    }

    @Test
    fun `when viewModel starts, the getStoredLocations use case is invoked`() {
        runBlocking {
            val storedLocations = mockedStoredLocations

            whenever(getStoredLocations.invoke()).thenReturn(flowOf(storedLocations))
            vm.storedLocations.observeForever(storedLocationsObserver)

            vm.startCollectingStoredLocations()

            verify(storedLocationsObserver).onChanged(storedLocations)
        }
    }
}