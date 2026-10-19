package com.example.myapplication

import android.app.Application
import com.example.myapplication.presentation.di.AppContainer

class NotepadApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
