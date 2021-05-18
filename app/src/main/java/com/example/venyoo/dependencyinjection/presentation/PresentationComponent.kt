package com.example.venyoo.dependencyinjection.presentation

import com.example.venyoo.screens.*
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {
    fun inject(searchVenueFragment: SearchVenueFragment)
    fun inject(venueListFragment: VenueListFragment)
    fun inject(venueDetailFragment: VenueDetailFragment)
    fun inject(venueEventDetailFragment: EventDetailFragment)
    fun inject(setlistBottomSheetDialogFragment: SetlistBottomSheetDialogFragment)
}