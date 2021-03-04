package com.example.usecases

import com.example.data.repo.PhotosRepo
import com.example.domain.Photo
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeleteSavedPhotoTest {
    @Mock
    lateinit var deleteSavedPhoto: DeleteSavedPhoto

    @Mock
    lateinit var photosRepo: PhotosRepo

    @Before
    fun setUp() {
        deleteSavedPhoto = DeleteSavedPhoto(photosRepo)
    }


    @Test
    fun `After DeleteSavedPhoto invoke method is call, photosRepo calls method deleteSavedPhoto`() {
        runBlocking {
            val photo = Photo().apply {
                id = "1"
                title = "Title test"
                isSaved = true
            }

            deleteSavedPhoto.invoke(photo)

            verify(photosRepo).deleteSavedPhoto(photo)
        }
    }
}