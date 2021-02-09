package com.example.venyoo.networking

import com.example.venyoo.venues.VenueListResponseSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterApi {

    @GET("venues?countryCode=US&radius=30&unit=miles")
    suspend fun fetchVenue(
            @Query("keyword") venueName: String,
    ): VenueListResponseSchema

    @GET("venues?countryCode=US&radius=30&unit=miles&sort=distance,asc")
    suspend fun fetchVenueByCoordinates(
            @Query("latlong") latLong: String
    ): VenueListResponseSchema
}