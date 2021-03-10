package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MarkPhotoAsFavoriteTest {

    @Mock
    lateinit var photosRepo: PhotosRepo

    @Mock
    lateinit var markPhotoAsFavorite: MarkPhotoAsFavorite

    @Before
    fun setUp() {
        markPhotoAsFavorite = MarkPhotoAsFavorite(photosRepo)
    }

    private val mockedPhoto = Photo().apply {
        id = "1"
        isSaved = true
    }

    @Test
    fun `After MarkPhotoAsFavorite calls invoke, verify that photosRepo calls insertSavedPhoto`() {
        runBlocking {
            whenever(photosRepo.insertSavedPhoto(mockedPhoto)).thenReturn(Unit)

            markPhotoAsFavorite.invoke(mockedPhoto)

            verify(photosRepo).insertSavedPhoto(mockedPhoto)
        }
    }
}
