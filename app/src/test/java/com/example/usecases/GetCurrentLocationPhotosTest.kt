package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCurrentLocationPhotosTest {

    @Mock
    lateinit var photosRepo: PhotosRepo
    @Mock lateinit var photo: Photo
    lateinit var getCurrentLocationPhotos: GetCurrentLocationPhotos

    @Before
    fun setUp() {
        getCurrentLocationPhotos = GetCurrentLocationPhotos(photosRepo)
    }

    @Test
    fun `get current location calls repo`() {
        runBlocking {
            val latitude = 0.0
            val longitude = 0.0
            whenever(photosRepo.getPhotosNearby(any(), any())).thenReturn(listOf(photo))

            val result = getCurrentLocationPhotos.invoke(latitude, longitude).first()

            assertEquals(photo, result)
        }
    }
}