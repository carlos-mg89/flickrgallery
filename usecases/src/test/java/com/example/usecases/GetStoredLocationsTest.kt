package com.example.usecases

import com.example.data.repo.StoredLocationsRepo
import com.example.domain.StoredLocation
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class GetStoredLocationsTest {

    @Mock
    private lateinit var storedLocationsRepo: StoredLocationsRepo

    private lateinit var getStoredLocations: GetStoredLocations

    @Before
    fun setUp() {
        getStoredLocations = GetStoredLocations(storedLocationsRepo)
    }

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

    @Test
    fun `after executing invoke, the storedLocationsRepo returns a list of StoredLocation`() {
        runBlocking {
            whenever( storedLocationsRepo.getAll() ).thenReturn(flowOf(mockedStoredLocations))

            getStoredLocations.invoke()

            verify(storedLocationsRepo).getAll()
        }
    }
}