package com.example.venyoo.screens

import com.example.venyoo.networking.TicketMasterApi
import com.example.venyoo.venues.VenueResponse
import javax.inject.Inject

class VenueRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi) {
    suspend fun fetchMultipleVenues(venueName: String): List<VenueResponse>{
        return ticketMasterApi.fetchMultipleVenues(venueName)._embedded.venues
    }

    suspend fun fetchMultipleVenuesByCoordinates(latLong: String): List<VenueResponse>{
        return ticketMasterApi.fetchMultipleVenuesByCoordinates(latLong)._embedded.venues
    }
}