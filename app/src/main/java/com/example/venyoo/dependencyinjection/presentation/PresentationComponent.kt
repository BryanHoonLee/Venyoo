package com.example.venyoo.dependencyinjection.presentation

import com.example.venyoo.screens.MainActivity
import com.example.venyoo.screens.SearchVenueFragment
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {
//    fun inject(mainActivity: MainActivity)
    fun inject(searchVenueFragment: SearchVenueFragment)
}