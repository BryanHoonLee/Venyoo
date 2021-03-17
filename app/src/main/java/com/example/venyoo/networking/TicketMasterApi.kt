package com.example.venyoo.networking

import com.example.venyoo.venues.TicketMasterEventSchema
import com.example.venyoo.venues.TicketMasterVenueSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterApi {

    @GET("venues?countryCode=US&radius=30&unit=miles&size=80")
    suspend fun fetchVenues(
            @Query("keyword") venueName: String,
    ): TicketMasterVenueSchema

    @GET("venues?countryCode=US&radius=30&unit=miles&sort=distance,asc&size=80")
    suspend fun fetchVenuesByCoordinates(
            @Query("latlong") latLong: String
    ): TicketMasterVenueSchema

    @GET("events?countryCode=US&radius=30&unit=miles")
    suspend fun fetchVenueEvents(
        @Query("venueId") venueId: String,
        @Query("postalCode") postalCode: String
    ): TicketMasterEventSchema
}