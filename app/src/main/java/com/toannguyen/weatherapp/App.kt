package com.toannguyen.weatherapp

import android.app.Application
import com.toannguyen.weatherapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@App)
            modules(
                repositoryModules +
                        useCaseModules +
                        networkModules +
                        viewModels +
                        mapperModule +
                        localModules
            )
        }
        instance = this
    }


    companion object {
        lateinit var instance: App
            private set
    }

}