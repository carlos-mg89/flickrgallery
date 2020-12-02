package com.example.flickrgallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flickrgallery.client.FlickrApiClient
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.LocalRepo

class MainViewModel(private val localRepo: LocalRepo) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    init {
        _photos.value = ArrayList()
    }

    suspend fun setPhotosAt(latitude: Double, longitude: Double) {
        val wayPointPhotosResult = FlickrApiClient.service.listPhotosNearLocation(latitude, longitude)
        val wayPointPhotos = wayPointPhotosResult.photos.photo

        localRepo.insertAllPhotos(wayPointPhotos)

        this._photos.postValue(wayPointPhotos)
    }
}