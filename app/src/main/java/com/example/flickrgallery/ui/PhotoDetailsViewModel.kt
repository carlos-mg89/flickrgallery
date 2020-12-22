package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepo
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDetailsViewModel(private val photoRepo: PhotoRepo) : ScopedViewModel() {

    private val _favoriteStatus = MutableLiveData(false)
    val favoriteStatus: LiveData<Boolean>
        get() = _favoriteStatus

    fun getPhotoInitialState(photo: Photo) {
        launch(Dispatchers.IO) {
            _favoriteStatus.postValue(isPhotoInDB(photo))
        }
    }

    private suspend fun isPhotoInDB(photo: Photo): Boolean {
        return photoRepo.get(photo.id) != null
    }

    fun toggleSaveStatus(photo: Photo) {
        launch(Dispatchers.IO) {
            val newFavoriteStatus = if (isPhotoInDB(photo)) {
                deletePhotoInList(photo)
                false
            } else {
                savePhotoToList(photo)
                true
            }
            _favoriteStatus.postValue(newFavoriteStatus)
        }
    }

    private fun deletePhotoInList(photo: Photo) {
        launch(Dispatchers.IO) {
            photoRepo.delete(photo)
        }
    }

    private fun savePhotoToList(photo: Photo) {
        launch(Dispatchers.IO) {
            photoRepo.insert(photo)
        }
    }

}
