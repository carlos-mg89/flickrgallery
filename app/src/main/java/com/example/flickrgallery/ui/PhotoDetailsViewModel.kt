package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDetailsViewModel(private val photoRepo: PhotoRepo) : ViewModel() {
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
        viewModelScope.launch(Dispatchers.IO) {
            photoRepo.insertOnePhoto(photo)
        }

    }
    fun deletePhotoInList(photo: Photo)
    {
        //TODO: delete photo in db
        viewModelScope.launch(Dispatchers.IO) {
            photoRepo.deleteOnePhoto(photo)
        }

    }


}
