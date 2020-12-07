package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepo

class PhotoDetailsViewModel(photoRepo: PhotoRepo) : ViewModel() {
    // TODO: Implement the ViewModel
    var prueba:String =""

    private val _favoriteStatus = MutableLiveData<Boolean>(false)
    val favoriteStatus:LiveData<Boolean>
    get() = _favoriteStatus

    fun toggleSaveStatus(){
        val isSaved:Boolean = favoriteStatus.value!!
        _favoriteStatus.postValue(!isSaved)
    }
    fun savePhotoToList(photo: Photo){
        //TODO: save photo to list
    }


}
