package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepo
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDetailsViewModel(private val photoRepo: PhotoRepo) : ScopedViewModel() {


    private val _favoriteStatus = MutableLiveData<Boolean>(false)
    val favoriteStatus: LiveData<Boolean>
        get() = _favoriteStatus


    fun toggleSaveStatus(photo: Photo) {
        launch(Dispatchers.IO) {
            if (photoInDB(photo)) {
                deletePhotoInList(photo)
                _favoriteStatus.postValue(false)
            } else {
                savePhotoToList(photo)
                _favoriteStatus.postValue(true)
            }
        }
    }

    fun getPhotoInitialState(photo: Photo) {
        launch(Dispatchers.IO) {
            _favoriteStatus.postValue(photoInDB(photo))
        }
    }

    private suspend fun photoInDB(photo: Photo): Boolean {
        return if(photoRepo.get(photo.id)!=null){
            photoRepo.get(photo.id).id == photo.id
        } else false
    }

    private fun savePhotoToList(photo: Photo) {

        launch(Dispatchers.IO) {
            photoRepo.insert(photo)
        }

    }

    private fun deletePhotoInList(photo: Photo) {
        launch(Dispatchers.IO) {
            photoRepo.delete(photo)
        }

    }

}
