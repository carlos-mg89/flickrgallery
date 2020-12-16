package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepo
import com.example.flickrgallery.ui.common.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDetailsViewModel(photoRepo: PhotoRepo) : ScopedViewModel() {



    private val _favoriteStatus = MutableLiveData<Boolean>(false)
    val favoriteStatus:LiveData<Boolean>
        get() = _favoriteStatus

    fun toggleSaveStatus(){
        val isSaved:Boolean = favoriteStatus.value!!
        _favoriteStatus.postValue(!isSaved)
    }
    fun savePhotoToList(photo: Photo){

        viewModelScope.launch(Dispatchers.IO) {
            photoRepo.insertOnePhoto(photo)
        }

    }
    fun deletePhotoInList(photo: Photo)
    {

        viewModelScope.launch(Dispatchers.IO) {
            photoRepo.deleteOnePhoto(photo)
        }

    }

}
