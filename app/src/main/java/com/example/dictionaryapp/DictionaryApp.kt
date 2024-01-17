package com.example.dictionaryapp

import android.app.Application
import com.example.dictionaryapp.di.koin.module
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DictionaryApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DictionaryApp)
            modules(module)
        }
    }
}
