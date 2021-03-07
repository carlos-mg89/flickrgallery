package com.example.flickrgallery.ui.savedPhotos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.Photo
import com.example.usecases.DeleteSavedPhoto
import com.example.usecases.GetSavedPhotos
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SavedPhotosViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var deleteSavedPhoto: DeleteSavedPhoto

    @Mock
    lateinit var getSavedPhotos: GetSavedPhotos

    @Mock
    lateinit var photosSavedObserver: Observer<List<Photo>>

    private lateinit var vm: SavedPhotosViewModel

    private val mockedPhotos = listOf(
        Photo().apply {
            id = "1"
            title = "Test Photo 1"
        },
        Photo().apply {
            id = "2"
            title = "Test Photo 2"
        })

    @Before
    fun setUp() {
        vm = SavedPhotosViewModel(getSavedPhotos, deleteSavedPhoto, Dispatchers.Unconfined)
    }

    @Test
    fun `when ViewModel deleteSavedPhoto is called, the deleteSavedPhoto is invoked`() {
        val storedPhotos = mockedPhotos
        runBlocking {
            whenever(deleteSavedPhoto.invoke(storedPhotos.first())).thenReturn(Unit)

            vm.deleteSavedPhoto(storedPhotos.first())

            verify(deleteSavedPhoto).invoke(storedPhotos.first())
        }
    }

    @Test
    fun `when the viewModel startCollection photos, the getSavedPhotos is invoked`() {
        runBlocking {
            val storedPhotos = mockedPhotos
            whenever(getSavedPhotos.invoke()).thenReturn(flowOf(storedPhotos))

            vm.savedPhotos.observeForever(photosSavedObserver)
            vm.startCollectingPhotos()

            verify(photosSavedObserver).onChanged(storedPhotos)
        }
    }

}