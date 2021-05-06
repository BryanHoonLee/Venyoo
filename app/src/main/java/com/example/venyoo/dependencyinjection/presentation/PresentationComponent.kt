package com.example.venyoo.dependencyinjection.presentation

import com.example.venyoo.screens.EventDetailFragment
import com.example.venyoo.screens.SearchVenueFragment
import com.example.venyoo.screens.VenueListFragment
import com.example.venyoo.screens.VenueDetailFragment
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {
    fun inject(searchVenueFragment: SearchVenueFragment)
    fun inject(venueListFragment: VenueListFragment)
    fun inject(venueDetailFragment: VenueDetailFragment)
    fun inject(venueEventDetailFragment: EventDetailFragment)
}