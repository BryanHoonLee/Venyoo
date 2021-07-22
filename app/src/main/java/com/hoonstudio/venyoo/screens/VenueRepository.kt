package com.hoonstudio.venyoo.screens

import com.hoonstudio.venyoo.networking.FourSquareApi
import com.hoonstudio.venyoo.networking.TicketMasterApi
import com.hoonstudio.venyoo.venues.*
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