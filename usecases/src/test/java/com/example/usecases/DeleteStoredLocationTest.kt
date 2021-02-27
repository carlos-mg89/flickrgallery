package com.example.usecases

import com.example.data.repo.StoredLocationsRepo
import com.example.domain.StoredLocation
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class DeleteStoredLocationTest {

    @Mock
    private lateinit var storedLocationsRepo: StoredLocationsRepo

    private lateinit var deleteStoredLocation: DeleteStoredLocation

    @Before
    fun setUp() {
        deleteStoredLocation = DeleteStoredLocation(storedLocationsRepo)
    }

    private val mockedStoredLocation = StoredLocation().apply {
        id = 1
        savedDate = Calendar.getInstance().time
        latitude = 42.10
        longitude = 2.35
        description = "Location #1"
    }

    @Test
    fun `after executing invoke, the storedLocationsRepo runs the delete method on the given StoredLocation`() {
        runBlocking {
            val storedLocation = mockedStoredLocation

            deleteStoredLocation.invoke(storedLocation)

            verify(storedLocationsRepo).delete(storedLocation)
        }
    }
}