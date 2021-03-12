package com.example.venyoo.networking

import com.example.venyoo.Constants
import com.example.venyoo.venues.FourSquareVenueResponseSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface FourSquareApi {
    @GET("venues/search?" +
            "radius=30&" +
            "limit=20&" +
            "categoryId=${Constants.FOURSQUARE_CATEGORY_MUSIC_VENUE}," +
            "${Constants.FOURSQUARE_CATEGORY_MUSIC_FESTIVAL_EVENT}," +
            "${Constants.FOURSQUARE_CATEGORY_CONCERT_HALL}, " +
            "${Constants.FOURSQUARE_CATEGORY_OPERA_HOUSE}")
    suspend fun fetchVenues(
        @Query("query") query: String
    ): FourSquareVenueResponseSchema

}