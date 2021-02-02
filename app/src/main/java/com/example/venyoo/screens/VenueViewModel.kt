package com.example.venyoo.screens

import androidx.lifecycle.*
import com.example.venyoo.venues.Venue
import kotlinx.coroutines.launch
import javax.inject.Inject

class VenueViewModel @Inject constructor(
        private val savedStateHandle: SavedStateHandle,
        private val venueRepository: VenueRepository
): ViewModel() {

    val venues: LiveData<List<Venue>> get() = savedStateHandle.getLiveData("venues")

    fun fetchVenue(venueName: String){
        viewModelScope.launch {
            savedStateHandle["venues"] = venueRepository.fetchVenue(venueName)
        }
    }
}