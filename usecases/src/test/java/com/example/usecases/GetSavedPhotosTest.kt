package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
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

    @Test
    fun `After GetSavedPhotos calls invoke, result's photos are the same than listPhotos`() {
        val listPhotos = flowOf(mockedPhotos)

        whenever(photosRepo.getAllSavedPhotos()).thenReturn(listPhotos)

        val result = getSavedPhotos.invoke()

        assertEquals(listPhotos, result)
    }
}
