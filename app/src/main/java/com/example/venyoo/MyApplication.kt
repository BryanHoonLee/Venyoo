package com.example.venyoo

import android.app.Application
import com.example.venyoo.dependencyinjection.app.AppComponent
import com.example.venyoo.dependencyinjection.app.AppModule
import com.example.venyoo.dependencyinjection.app.DaggerAppComponent

class MyApplication: Application() {

    val appComponent: AppComponent by lazy{
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}