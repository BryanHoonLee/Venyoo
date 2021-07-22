package com.hoonstudio.venyoo

import android.app.Application
import com.hoonstudio.venyoo.dependencyinjection.app.AppComponent
import com.hoonstudio.venyoo.dependencyinjection.app.AppModule
import com.hoonstudio.venyoo.dependencyinjection.app.DaggerAppComponent

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