package com.example.venyoo.screens

import android.util.Log
import com.example.venyoo.networking.TicketMasterApi
import com.example.venyoo.venues.Venue
import javax.inject.Inject

class VenueRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi) {
    suspend fun fetchVenue(venueName: String): List<Venue>{
        Log.d("AHHH", "${ticketMasterApi.fetchVenue(venueName)._embedded.venues[0].name}")
        return ticketMasterApi.fetchVenue(venueName)._embedded.venues
    }
}