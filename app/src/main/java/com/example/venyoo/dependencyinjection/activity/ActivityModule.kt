package com.example.venyoo.dependencyinjection.activity

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
     val activity: AppCompatActivity
) {

    @Provides
    fun activity() = activity

}