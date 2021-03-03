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

@RunWith(MockitoJUnitRunner::class)
class SaveStoredLocationTest {

    @Mock
    private lateinit var storedLocationsRepo: StoredLocationsRepo

    private lateinit var saveStoredLocation: SaveStoredLocation

    private val location = StoredLocation()

    @Before
    fun setUp() {
        saveStoredLocation = SaveStoredLocation(storedLocationsRepo)
    }

    @Test
    fun `invoke calls insert in repo`() {
        runBlocking {
            saveStoredLocation.invoke(location)
            verify(storedLocationsRepo).insert(location)
        }
    }
}