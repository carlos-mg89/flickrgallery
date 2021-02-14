package com.example.flickrgallery.ui.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher


abstract class ScopedViewModelWithCustomDispatcher (uiDispatcher: CoroutineDispatcher) : ViewModel(),
    ScopeWithDispatcher by ScopeWithDispatcher.Impl(uiDispatcher) {

    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}