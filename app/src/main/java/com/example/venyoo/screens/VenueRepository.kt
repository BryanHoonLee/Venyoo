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

    suspend fun fetchFourSquareVenue(latlng: String): List<FourSquareVenueResponse>{
        return fourSquareApi.fetchFourSquareVenue(latlng).response.venues
    }

    suspend fun fetchFourSquarePhotos(venueId: String): List<FourSquareImageItem>{
        return fourSquareApi.fetchFourSquarePhotos(venueId).response.photos.items
    }
}