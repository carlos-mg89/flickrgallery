package com.example.flickrgallery.ui

import androidx.lifecycle.*
import com.example.flickrgallery.client.FlickrApiClient
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.GpsRepo
import com.example.flickrgallery.repo.LocalRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val localRepo: LocalRepo,
    private val gpsRepo: GpsRepo
) : ViewModel() {

    private val _progressVisible = MutableLiveData<Boolean>()
    val progressVisible: LiveData<Boolean>
        get() = _progressVisible

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    fun proceedGettingUpdates() {
        if (gpsRepo.areUpdatesDisabled) {
            gpsRepo.areUpdatesDisabled = false
            gpsRepo.setAccurateLocationListener {
                viewModelScope.launch(Dispatchers.IO) {
                    postPhotosAt(it.latitude, it.longitude)
                }
            }
        }
    }

    private suspend fun postPhotosAt(latitude: Double, longitude: Double) {
        _progressVisible.postValue(true)
        val photos = getPhotos(latitude, longitude)
        _photos.postValue(photos)
        _progressVisible.postValue(false)
    }

    private suspend fun getPhotos(latitude: Double, longitude: Double): List<Photo> {
        val wayPointPhotosResult = FlickrApiClient.service.listPhotosNearLocation(latitude, longitude)
        return wayPointPhotosResult.photos.photo
    }
}