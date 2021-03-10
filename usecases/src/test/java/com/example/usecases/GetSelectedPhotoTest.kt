package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetSelectedPhotoTest {

    @Mock
    lateinit var photosRepo: PhotosRepo

    @Mock
    lateinit var getSelectedPhoto: GetSelectedPhoto

    @Before
    fun setUp() {
        getSelectedPhoto = GetSelectedPhoto(photosRepo)
    }

    private val mockedPhoto = Photo().apply {
        id = "1"
        isSaved = true
    }

    @Test
    fun `After GetSelectedPhoto calls invoke, result photo is the same than mockedPhoto`() {
        runBlocking {
            whenever(photosRepo.getSavedPhoto(mockedPhoto.id)).thenReturn(mockedPhoto)

            val result = getSelectedPhoto.invoke(mockedPhoto.id)

            assertEquals(mockedPhoto, result)
            verify(photosRepo).getSavedPhoto(mockedPhoto.id)
        }
    }
}
