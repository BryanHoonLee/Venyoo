package com.example.venyoo.screens

import android.util.Log
import com.example.venyoo.networking.FourSquareApi
import com.example.venyoo.networking.TicketMasterApi
import com.example.venyoo.venues.*
import javax.inject.Inject

class VenueRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi, private val fourSquareApi: FourSquareApi) {
    suspend fun fetchVenues(venueName: String): List<TicketMasterVenueResponse>{
        return ticketMasterApi.fetchVenues(venueName)._embedded.venues
    }

    suspend fun fetchVenuesByCoordinates(latLong: String): List<TicketMasterVenueResponse>{
        return ticketMasterApi.fetchVenuesByCoordinates(latLong)._embedded.venues
    }

    suspend fun fetchVenueEvents(venueId: String, postalCode: String): List<TicketMasterEventResponse>{
        return ticketMasterApi.fetchVenueEvents(venueId, postalCode)._embedded.events
    }

    suspend fun fetchFourSquareVenue(latlng: String): List<FourSquareVenueResponse>{
        return fourSquareApi.fetchFourSquareVenue(latlng).response.venues
    }

    suspend fun fetchFourSquarePhotos(venueId: String): List<FourSquareImageItem>{
        Log.d("TEST126", "repo: ${venueId}")
        val test = fourSquareApi.fetchFourSquarePhotos(venueId)
        Log.d("TEST129", "test: ${test}")
        return fourSquareApi.fetchFourSquarePhotos(venueId).response.photos.items
    }
}