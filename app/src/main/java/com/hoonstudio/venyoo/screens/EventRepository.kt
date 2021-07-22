package com.hoonstudio.venyoo.screens

import com.hoonstudio.venyoo.networking.SetlistFMApi
import com.hoonstudio.venyoo.networking.TicketMasterApi
import com.hoonstudio.venyoo.venues.SetlistResponse
import com.hoonstudio.venyoo.venues.TicketMasterEventResponse
import javax.inject.Inject

class EventRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi, private val setlistFMApi: SetlistFMApi) {

    suspend fun fetchVenueEvents(venueId: String): List<TicketMasterEventResponse>{
        return ticketMasterApi.fetchVenueEvents(venueId)._embedded.events
    }

    suspend fun fetchSetlist(artistName: String): List<SetlistResponse>{
        return setlistFMApi.fetchSetList(artistName).setlist
    }
}