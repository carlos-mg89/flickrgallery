package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickrgallery.repo.LocalRepo


@Suppress("UNCHECKED_CAST")
class PhotoDetailsViewModelFactory constructor(
    private val localRepo: LocalRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
            PhotoDetailsViewModel(localRepo) as T
}

