package com.example.venyoo.screens

import com.example.venyoo.networking.SetlistFMApi
import com.example.venyoo.networking.TicketMasterApi
import com.example.venyoo.venues.SetlistResponse
import com.example.venyoo.venues.TicketMasterEventResponse
import javax.inject.Inject

class EventRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi, private val setlistFMApi: SetlistFMApi) {

    suspend fun fetchVenueEvents(venueId: String): List<TicketMasterEventResponse>{
        return ticketMasterApi.fetchVenueEvents(venueId)._embedded.events
    }

    suspend fun fetchSetlist(artistTicketMasterId: String): List<SetlistResponse>{
        return setlistFMApi.fetchSetList(artistTicketMasterId).setlist
    }
}