package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickrgallery.repo.LocalRepo


@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val localRepo: LocalRepo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(localRepo)

                isAssignableFrom(PhotoDetailsViewModel::class.java) ->
                    PhotoDetailsViewModel(localRepo)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}

