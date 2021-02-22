package com.example.flickrgallery.ui.common

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

interface ScopeWithDispatcher: CoroutineScope {

    class Impl (override val uiDispatcher: CoroutineDispatcher) : ScopeWithDispatcher {
        override lateinit var job: Job
    }

    var job: Job
    val uiDispatcher: CoroutineDispatcher
    override val coroutineContext: CoroutineContext
        get() = uiDispatcher + job

    fun initScope() {
        job = SupervisorJob()
    }

    fun destroyScope() {
        job.cancel()
    }
}