package com.example.flickrgallery

import android.app.Application
import com.example.flickrgallery.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApp: Application() {

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@BaseApp)
            modules(appModule, dataSourceModule, repoModule, useCasesModule, scopedModule)
        }
    }
}