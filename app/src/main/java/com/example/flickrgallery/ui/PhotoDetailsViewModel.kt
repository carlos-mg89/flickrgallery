package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.repo.PhotosRepo
import com.example.flickrgallery.data.source.toDomainPhoto
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDetailsViewModel(private val photosRepo: PhotosRepo) : ScopedViewModel() {

    private val _favoriteStatus = MutableLiveData(false)
    val favoriteStatus: LiveData<Boolean>
        get() = _favoriteStatus

    fun checkIfPhotoExists(photo: Photo) {
        launch(Dispatchers.IO) {
            _favoriteStatus.postValue(isPhotoInDB(photo))
        }
    }

    private suspend fun isPhotoInDB(photo: Photo): Boolean {
        return photosRepo.getSavedPhoto(photo.id) != null
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
            photosRepo.deleteSavedPhoto(photo.toDomainPhoto())
        }
    }

    private fun savePhotoToList(photo: Photo) {
        launch(Dispatchers.IO) {
            photosRepo.insertSavedPhoto(photo.toDomainPhoto())
        }
    }

}
