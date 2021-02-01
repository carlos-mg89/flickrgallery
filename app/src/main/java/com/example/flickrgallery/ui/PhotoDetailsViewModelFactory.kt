package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repo.PhotosRepo
import com.example.usecases.GetSelectedPhoto
import com.example.usecases.MarkPhotoAsFavorite
import com.example.usecases.UnMarkPhotoAsFavorite


@Suppress("UNCHECKED_CAST")
class PhotoDetailsViewModelFactory constructor(
    private val getSelectedPhoto: GetSelectedPhoto,
    private val markPhotoAsFavorite: MarkPhotoAsFavorite,
    private val unMarkPhotoAsFavorite: UnMarkPhotoAsFavorite
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            PhotoDetailsViewModel(getSelectedPhoto, markPhotoAsFavorite, unMarkPhotoAsFavorite) as T
}

