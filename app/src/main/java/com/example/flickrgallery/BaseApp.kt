package com.example.flickrgallery

import android.app.Application
import com.example.flickrgallery.di.appModule
import com.example.flickrgallery.di.dataSourceModule
import com.example.flickrgallery.di.repoModule
import com.example.flickrgallery.di.useCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@BaseApp)
            modules(appModule, dataSourceModule, repoModule, useCasesModule)
        }
    }
}