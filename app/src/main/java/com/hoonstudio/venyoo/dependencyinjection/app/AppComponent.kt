package com.hoonstudio.venyoo.dependencyinjection.app

import com.hoonstudio.venyoo.dependencyinjection.activity.ActivityComponent
import com.hoonstudio.venyoo.dependencyinjection.activity.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
}