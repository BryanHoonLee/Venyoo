package com.hoonstudio.venyoo.dependencyinjection.activity

import com.hoonstudio.venyoo.dependencyinjection.presentation.PresentationComponent
import com.hoonstudio.venyoo.dependencyinjection.presentation.PresentationModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent
}