package com.example.venyoo.networking

import com.example.venyoo.venues.VenueListResponseSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterApi {

    @GET("venues?countryCode=US")
    suspend fun fetchVenue(
            @Query("keyword") venueName: String,
    ): VenueListResponseSchema

}