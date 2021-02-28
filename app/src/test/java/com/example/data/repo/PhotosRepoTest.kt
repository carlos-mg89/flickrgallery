package com.example.data.repo

import com.example.data.source.PhotosLocalDataSource
import com.example.data.source.PhotosRemoteDataSource
import com.example.domain.Photo
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhotosRepoTest {

    lateinit var photosRepo: PhotosRepo
    @Mock lateinit var local: PhotosLocalDataSource
    @Mock lateinit var remote: PhotosRemoteDataSource
    private val photo = Photo()

    @Before
    fun setUp() {
        photosRepo = PhotosRepo(local, remote)
    }

    @Test
    fun `getPhotosNearby calls remote source`() {
        runBlocking {
            whenever(remote.getPhotosNearby(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(listOf(photo))

            val expected = photo
            val actual = photosRepo.getPhotosNearby(0.0, 0.0).first()

            Assert.assertEquals(actual, expected)
        }
    }
}