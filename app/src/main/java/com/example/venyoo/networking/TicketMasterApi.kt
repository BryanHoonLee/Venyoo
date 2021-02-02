package com.example.venyoo.networking

import com.example.venyoo.venues.Venue
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterApi {

    @GET("/discovery/v2/venues")
    suspend fun fetchVenue(
            @Query("keyword") venueName: String
    ): List<Venue>

}