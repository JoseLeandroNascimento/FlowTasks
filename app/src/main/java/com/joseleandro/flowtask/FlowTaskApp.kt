package com.joseleandro.flowtask

import android.app.Application
import com.joseleandro.flowtask.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlowTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FlowTaskApp)
            modules(appModule)
        }
    }

}