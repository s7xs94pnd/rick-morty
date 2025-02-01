package com.example.rickmorty.app

import android.app.Application
import com.example.rickmorty.data.serviseLocator.dataModule
import com.example.rickmorty.ui.servisceLocator.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, uiModule)
        }
    }
}