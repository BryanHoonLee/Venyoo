package com.hoonstudio.venyoo.dependencyinjection.presentation

import com.hoonstudio.venyoo.screens.*
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