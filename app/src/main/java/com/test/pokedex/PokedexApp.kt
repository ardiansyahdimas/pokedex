package com.test.pokedex

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
open class PokedexApp: Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        private lateinit var appContext: Context
        val context: Context get() = appContext
    }
}