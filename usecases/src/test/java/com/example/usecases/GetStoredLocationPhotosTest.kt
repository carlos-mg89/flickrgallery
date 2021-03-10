package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo
import com.example.domain.StoredLocation
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetStoredLocationPhotosTest {

    private val mockedStoredLocation = StoredLocation().apply {
        id = 1
        description = "Another testable StoredLocation"
    }

    private val mockedPhotos = listOf(
        Photo().apply {
            id = "1"
            isSaved = true
        },
        Photo().apply {
            id = "2"
            isSaved = true
        }
    )

    @Mock
    lateinit var photosRepo: PhotosRepo

    @Mock
    lateinit var getStoredLocationPhotos: GetStoredLocationPhotos

    @Before
    fun setUp() {
        getStoredLocationPhotos = GetStoredLocationPhotos(photosRepo)
    }

    @Test
    fun `After GetStoredLocationPhotos calls invoke, result's photos are the same than listPhotos`() {
        runBlocking {
            whenever(
                photosRepo.getPhotosNearby(
                    mockedStoredLocation.latitude,
                    mockedStoredLocation.longitude
                )
            ).thenReturn(mockedPhotos)

            val result = getStoredLocationPhotos.invoke(mockedStoredLocation)

            assertEquals(mockedPhotos, result)
        }
    }
}
