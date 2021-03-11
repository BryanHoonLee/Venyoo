package com.example.venyoo.networking

import com.example.venyoo.venues.TicketMasterVenueListResponseSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface uTicketMasterApi {

    @GET("venues?countryCode=US&radius=30&unit=miles")
    suspend fun fetchMultipleVenues(
            @Query("keyword") venueName: String,
    ): TicketMasterVenueListResponseSchema

    @GET("venues?countryCode=US&radius=30&unit=miles&sort=distance,asc")
    suspend fun fetchMultipleVenuesByCoordinates(
            @Query("latlong") latLong: String
    ): TicketMasterVenueListResponseSchema
}