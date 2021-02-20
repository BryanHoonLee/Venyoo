package com.example.venyoo.screens

import android.util.Log
import androidx.lifecycle.*
import com.example.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.example.venyoo.venues.VenueResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class VenueViewModel @Inject constructor(
        private val venueRepository: VenueRepository
): SavedStateViewModel() {


    val venues: LiveData<List<VenueResponse>> get() = savedStateHandle.getLiveData("venues")

    fun test(string: String){
        Log.d(string, "${savedStateHandle.getLiveData<List<VenueResponse>>("venues")}")
    }

    fun fetchMultipleVenues(venueName: String){
        viewModelScope.launch {
            savedStateHandle["venues"] = venueRepository.fetchMultipleVenues(venueName)
        }
    }

    fun fetchMultipleVenuesByCoordinates(latLong: String){
        viewModelScope.launch {
            Log.d("TEST6", "${venues.value?.size}")
            savedStateHandle["venues"] = venueRepository.fetchMultipleVenuesByCoordinates(latLong)
            Log.d("TEST7", "${venues.value?.size}")

        }
    }
}