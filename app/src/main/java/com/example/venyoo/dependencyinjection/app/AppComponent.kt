package com.example.venyoo.dependencyinjection.app

import com.example.venyoo.dependencyinjection.activity.ActivityComponent
import com.example.venyoo.dependencyinjection.activity.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
}