package com.example.venyoo.screens

import android.util.Log
import androidx.lifecycle.*
import com.example.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.example.venyoo.venues.TicketMasterEventResponse
import com.example.venyoo.venues.TicketMasterVenueImage
import com.example.venyoo.venues.TicketMasterVenueResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class VenueViewModel @Inject constructor(
        private val venueRepository: VenueRepository
): SavedStateViewModel() {

    companion object{
        const val SAVED_STATE_HANDLE_VENUE_LIST = "venues"
        const val SAVED_STATE_HANDLE_VENUE = "venue"
        const val SAVED_STATE_HANDLE_EVENTS = "events"
        const val SAVED_STATE_HANDLE_IMAGES = "images"
    }


    val venueList: LiveData<List<TicketMasterVenueResponse>> get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_VENUE_LIST)

    val currentVenue: LiveData<TicketMasterVenueResponse> get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_VENUE)

    val venueEventList: LiveData<List<TicketMasterEventResponse>> get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_EVENTS)

    val venueImageList: LiveData<List<TicketMasterVenueImage>> get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_IMAGES)

    fun updateCurrentVenue(venue: TicketMasterVenueResponse){
        savedStateHandle[SAVED_STATE_HANDLE_VENUE] = venue
    }

    fun fetchVenues(venueName: String){
        viewModelScope.launch {
            val venues: MutableList<TicketMasterVenueResponse> = venueRepository.fetchVenues(venueName).toMutableList()
            savedStateHandle[SAVED_STATE_HANDLE_VENUE_LIST] = venues.filter { response ->
                !response.images.isNullOrEmpty() || !response.social.twitter.handle.isNullOrEmpty() || !response.boxOfficeInfo.phoneNumberDetail.isNullOrEmpty()
            }

//            savedStateHandle[SAVED_STATE_HANDLE_VENUE_LIST] = venueRepository.fetchVenues(venueName)
        }
    }

    fun fetchVenuesByCoordinates(latLong: String){
        viewModelScope.launch {
            val venues: MutableList<TicketMasterVenueResponse> = venueRepository.fetchVenuesByCoordinates(latLong).toMutableList()
            savedStateHandle[SAVED_STATE_HANDLE_VENUE_LIST]  = venues.filter { response ->
                !response.images.isNullOrEmpty() || !response.social.twitter.handle.isNullOrEmpty() || !response.boxOfficeInfo.phoneNumberDetail.isNullOrEmpty()
            }
        }
    }

    fun fetchVenueEvents(venueId: String, postalCode: String){
        viewModelScope.launch {
            savedStateHandle[SAVED_STATE_HANDLE_EVENTS] = venueRepository.fetchVenueEvents(venueId, postalCode)
        }
    }
}