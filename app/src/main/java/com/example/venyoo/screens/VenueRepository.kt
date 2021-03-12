package com.example.venyoo.screens

import com.example.venyoo.networking.TicketMasterApi
import com.example.venyoo.venues.TicketMasterEventResponse
import com.example.venyoo.venues.TicketMasterVenueResponse
import javax.inject.Inject

class VenueRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi) {
    suspend fun fetchVenues(venueName: String): List<TicketMasterVenueResponse>{
        return ticketMasterApi.fetchVenues(venueName)._embedded.venues
    }

    suspend fun fetchVenuesByCoordinates(latLong: String): List<TicketMasterVenueResponse>{
        return ticketMasterApi.fetchVenuesByCoordinates(latLong)._embedded.venues
    }

    suspend fun fetchVenueEvents(venueId: String, postalCode: String): List<TicketMasterEventResponse>{
        return ticketMasterApi.fetchVenueEvents(venueId, postalCode)._embedded.events
    }
}