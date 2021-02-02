package com.example.venyoo.screens

import com.example.venyoo.networking.TicketMasterApi
import com.example.venyoo.venues.Venue
import javax.inject.Inject

class VenueRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi) {
    suspend fun fetchVenue(venueName: String): List<Venue>{
        return ticketMasterApi.fetchVenue(venueName)
    }
}