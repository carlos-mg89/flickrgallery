package com.example.flickrgallery.ui.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher

abstract class ScopedViewModel() : ViewModel(),
    Scope by Scope.Impl() {

    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}