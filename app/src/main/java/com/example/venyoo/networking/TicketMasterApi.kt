package com.example.venyoo.networking

import com.example.venyoo.venues.Venue
import com.example.venyoo.venues.VenueListResponseSchema
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterApi {

    @GET("venues")
    suspend fun fetchVenue(
            @Query("keyword") venueName: String
    ): VenueListResponseSchema

}