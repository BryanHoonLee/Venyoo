package com.hoonstudio.venyoo.screens

import com.hoonstudio.venyoo.networking.TicketMasterApi
import com.hoonstudio.venyoo.venues.TicketMasterAttractionResponse
import javax.inject.Inject

class AttractionRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi) {

    suspend fun fetchAttractions(attractionName: String): List<TicketMasterAttractionResponse>{
        return ticketMasterApi.fetchAttractions(attractionName)._embedded.attractions
    }
}