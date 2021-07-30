package com.hoonstudio.venyoo.networking

import com.hoonstudio.venyoo.venues.TicketMasterAttractionSchema
import com.hoonstudio.venyoo.venues.TicketMasterEventSchema
import com.hoonstudio.venyoo.venues.TicketMasterVenueSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterApi {

    @GET("venues?countryCode=US&radius=60&unit=miles&size=60&sort=relevance,desc")
    suspend fun fetchVenues(
            @Query("keyword") venueName: String,
    ): TicketMasterVenueSchema

    @GET("venues?countryCode=US&radius=60&unit=miles&sort=relevance,desc&size=60")
    suspend fun fetchVenuesByCoordinates(
            @Query("latlong") latLong: String
    ): TicketMasterVenueSchema

    @GET("events?countryCode=US&radius=30&unit=miles&size=60")
    suspend fun fetchVenueEventsByVenueId(
        @Query("venueId") venueId: String
    ): TicketMasterEventSchema

    @GET("attractions?size=60&sort=relevance,desc")
    suspend fun fetchAttractions(
        @Query("keyword") attractionName: String,
    ): TicketMasterAttractionSchema

    @GET("events?countryCode=US&radius=30&unit=miles&size=60")
    suspend fun fetchEventsByAttractionId(
        @Query("attractionId") attractionId: String
    ): TicketMasterEventSchema
}