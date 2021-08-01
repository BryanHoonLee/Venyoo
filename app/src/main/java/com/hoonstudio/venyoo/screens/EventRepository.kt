package com.hoonstudio.venyoo.screens

import com.hoonstudio.venyoo.networking.SetlistFMApi
import com.hoonstudio.venyoo.networking.TicketMasterApi
import com.hoonstudio.venyoo.venues.SetlistResponse
import com.hoonstudio.venyoo.venues.TicketMasterAttractionResponse
import com.hoonstudio.venyoo.venues.TicketMasterEventResponse
import javax.inject.Inject

class EventRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi, private val setlistFMApi: SetlistFMApi) {

    suspend fun fetchVenueEvents(venueId: String): List<TicketMasterEventResponse>{
        return ticketMasterApi.fetchVenueEventsByVenueId(venueId)._embedded.events
    }

    suspend fun fetchVenueEventsByAttractionId(attractionId: String): List<TicketMasterEventResponse>{
        return ticketMasterApi.fetchEventsByAttractionId(attractionId)._embedded.events
    }

    suspend fun fetchSetlist(artistName: String): List<SetlistResponse>{
        return removeEmptySetLists(setlistFMApi.fetchSetList(artistName).setlist)
    }

    private fun removeEmptySetLists(setlists: List<SetlistResponse>): List<SetlistResponse>{
        var sets: MutableList<SetlistResponse> = setlists.toMutableList()
        var remove = true
        while(remove){
            if(sets[0].sets.set.isNullOrEmpty()){
                sets.removeAt(0)
            }else{
                remove = false
            }
        }
        return sets
    }

    suspend fun fetchAttractions(attractionName: String): List<TicketMasterAttractionResponse>{
        return ticketMasterApi.fetchAttractions(attractionName)._embedded.attractions
    }
}