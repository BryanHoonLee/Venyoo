package com.example.venyoo.dependencyinjection.presentation

import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {
//    fun inject()
}