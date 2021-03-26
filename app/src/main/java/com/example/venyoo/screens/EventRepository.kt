package com.example.venyoo.screens

import com.example.venyoo.networking.TicketMasterApi
import com.example.venyoo.venues.TicketMasterEventResponse
import javax.inject.Inject

class EventRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi) {

    suspend fun fetchVenueEvents(venueId: String): List<TicketMasterEventResponse>{
        return ticketMasterApi.fetchVenueEvents(venueId)._embedded.events
    }
}