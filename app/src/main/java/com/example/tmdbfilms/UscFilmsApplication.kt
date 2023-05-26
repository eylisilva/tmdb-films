package com.example.tmdbfilms

import android.app.Application

class UscFilmsApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}