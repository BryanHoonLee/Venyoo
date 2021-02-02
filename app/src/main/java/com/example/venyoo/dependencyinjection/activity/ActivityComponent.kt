package com.example.venyoo.dependencyinjection.activity

import com.example.venyoo.dependencyinjection.presentation.PresentationComponent
import com.example.venyoo.dependencyinjection.presentation.PresentationModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent
}