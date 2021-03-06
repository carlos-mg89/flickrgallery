package com.example.flickrgallery.ui.photoDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.Photo
import com.example.flickrgallery.ui.common.ScopedViewModel
import com.example.usecases.GetSelectedPhoto
import com.example.usecases.MarkPhotoAsFavorite
import com.example.usecases.UnMarkPhotoAsFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch


class PhotoDetailsViewModel(
    private val getSelectedPhoto: GetSelectedPhoto,
    private val markPhotoAsFavorite: MarkPhotoAsFavorite,
    private val unMarkPhotoAsFavorite: UnMarkPhotoAsFavorite,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _favoriteStatus = MutableLiveData(false)
    val favoriteStatus: LiveData<Boolean>
        get() = _favoriteStatus

    fun checkIfPhotoExists(photo: Photo) {
        launch {
            if (getSelectedPhoto.invoke(photo.id) != null) {
                _favoriteStatus.value = true
            }
        }
    }

    fun toggleSaveStatus(photo: Photo) {
        val newFavoriteStatus = if (favoriteStatus.value == true) {
            deletePhotoInList(photo)
            false
        } else {
            savePhotoToList(photo)
            true
        }
        _favoriteStatus.value = newFavoriteStatus
    }

    private fun deletePhotoInList(photo: Photo) {
        launch {
            unMarkPhotoAsFavorite.invoke(photo)
        }
    }

    private fun savePhotoToList(photo: Photo) {
        launch {
            markPhotoAsFavorite.invoke(photo)
        }
    }
}