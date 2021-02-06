package com.example.flickrgallery.ui.photoDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flickrgallery.data.source.toDomainPhoto
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.ui.common.ScopedViewModel
import com.example.usecases.GetSelectedPhoto
import com.example.usecases.MarkPhotoAsFavorite
import com.example.usecases.UnMarkPhotoAsFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDetailsViewModel(
    private val getSelectedPhoto: GetSelectedPhoto,
    private val markPhotoAsFavorite: MarkPhotoAsFavorite,
    private val unMarkPhotoAsFavorite: UnMarkPhotoAsFavorite
) : ScopedViewModel() {

    private val _favoriteStatus = MutableLiveData(false)
    val favoriteStatus: LiveData<Boolean>
        get() = _favoriteStatus

    fun checkIfPhotoExists(photo: Photo) {
        launch(Dispatchers.IO) {
            _favoriteStatus.postValue(isPhotoInDB(photo))
        }
    }

    private suspend fun isPhotoInDB(photo: Photo): Boolean {
        return getSelectedPhoto.invoke(photo.id) != null
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
            unMarkPhotoAsFavorite.invoke(photo.toDomainPhoto())
        }
    }

    private fun savePhotoToList(photo: Photo) {
        launch(Dispatchers.IO) {
            markPhotoAsFavorite.invoke(photo.toDomainPhoto())
        }
    }

}
