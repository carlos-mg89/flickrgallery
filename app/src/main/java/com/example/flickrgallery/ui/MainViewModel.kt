package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flickrgallery.client.FlickrApiClient
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.LocalRepo

class MainViewModel(private val localRepo: LocalRepo) : ViewModel() {

    private val _progressVisible = MutableLiveData<Boolean>()
    val progressVisible: LiveData<Boolean>
        get() = _progressVisible

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    suspend fun setPhotosAt(latitude: Double, longitude: Double) {
        _progressVisible.postValue(true)
        _photos.postValue(getPhotos(latitude, longitude))
        _progressVisible.postValue(false)
    }

    private suspend fun getPhotos(latitude: Double, longitude: Double): List<Photo> {
        val wayPointPhotosResult = FlickrApiClient.service.listPhotosNearLocation(latitude, longitude)
        val wayPointPhotos = wayPointPhotosResult.photos.photo

        localRepo.insertAllPhotos(wayPointPhotos)

        return wayPointPhotos
    }
}