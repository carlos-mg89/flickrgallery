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
class UnMarkPhotoAsFavoriteTest {

    @Mock
    lateinit var photosRepo: PhotosRepo

    @Mock
    lateinit var unMarkPhotoAsFavorite: UnMarkPhotoAsFavorite

    @Before
    fun setUp() {
        unMarkPhotoAsFavorite = UnMarkPhotoAsFavorite(photosRepo)
    }

    private val mockedPhoto = Photo().apply {
        id = "1"
        isSaved = true
    }

    @Test
    fun `After UnMarkPhotoAsFavorite calls invoke, verify that photosRepo calls deleteSavedPhoto`() {
        runBlocking {
            whenever(photosRepo.deleteSavedPhoto(mockedPhoto)).thenReturn(Unit)

            unMarkPhotoAsFavorite.invoke(mockedPhoto)

            verify(photosRepo).deleteSavedPhoto(mockedPhoto)
        }
    }
}
