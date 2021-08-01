package com.hoonstudio.venyoo.dependencyinjection.presentation

import com.hoonstudio.venyoo.screens.*
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(attractionListFragment: AttractionListFragment)
    fun inject(attractionEventListFragment: AttractionEventListFragment)
    fun inject(venueListFragment: VenueListFragment)
    fun inject(venueDetailFragment: VenueDetailFragment)
    fun inject(venueEventDetailFragment: EventDetailFragment)
    fun inject(setlistBottomSheetDialogFragment: SetlistBottomSheetDialogFragment)
}