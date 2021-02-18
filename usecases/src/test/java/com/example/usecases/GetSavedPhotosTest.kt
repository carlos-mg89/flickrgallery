package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.testshared.mockedPhoto
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetSavedPhotosTest {

    @Mock
    lateinit var photosRepo: PhotosRepo

    @Mock
    lateinit var getSavedPhotos: GetSavedPhotos

    @Before
    fun setUp() {
        getSavedPhotos = GetSavedPhotos(photosRepo)
    }

    @Test
    fun `GetSavedPhotos calls `() {
        //GIVEN
        val photoA = mockedPhoto.apply {
            this.isSaved = true
            this.id = "1"
        }
        val photoB = mockedPhoto.apply {
            this.isSaved = true
            this.id = "2"
        }

        val listPhotos = listOf(photoA, photoB)

        val flow = flow {
            emit(listPhotos)
        }
        whenever(photosRepo.getAllSavedPhotos()).thenReturn(flow)
        //WHEN
        val result = getSavedPhotos.invoke()
        //THEN
        assertEquals(flow, result)
    }
}
