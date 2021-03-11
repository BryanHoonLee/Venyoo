package com.example.venyoo.screens

import android.util.Log
import androidx.lifecycle.*
import com.example.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.example.venyoo.venues.TicketMasterVenueResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class VenueViewModel @Inject constructor(
        private val venueRepository: VenueRepository
): SavedStateViewModel() {

    companion object{
        const val SAVED_STATE_HANDLE_VENUE_LIST = "venues"
        const val SAVED_STATE_HANDLE_VENUE = "venue"
    }


    val venueList: LiveData<List<TicketMasterVenueResponse>> get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_VENUE_LIST)

    val currentVenue: LiveData<TicketMasterVenueResponse> get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_VENUE)

    fun test(string: String){
        Log.d(string, "${savedStateHandle.getLiveData<List<TicketMasterVenueResponse>>(SAVED_STATE_HANDLE_VENUE_LIST)} + ${savedStateHandle}")
        Log.d(string , "${savedStateHandle.keys()}")
        Log.d(string , "${savedStateHandle.get<List<TicketMasterVenueResponse>>(SAVED_STATE_HANDLE_VENUE_LIST)}")

    }

    fun updateCurrentVenue(venue: TicketMasterVenueResponse){
        savedStateHandle[SAVED_STATE_HANDLE_VENUE] = venue
    }

    fun fetchMultipleVenues(venueName: String){
        viewModelScope.launch {
            savedStateHandle[SAVED_STATE_HANDLE_VENUE_LIST] = venueRepository.fetchMultipleVenues(venueName)
        }
    }

    fun fetchMultipleVenuesByCoordinates(latLong: String){
        viewModelScope.launch {
            savedStateHandle[SAVED_STATE_HANDLE_VENUE_LIST] = venueRepository.fetchMultipleVenuesByCoordinates(latLong)
        }
    }
}