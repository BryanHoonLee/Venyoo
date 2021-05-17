package com.example.venyoo.networking

import com.example.venyoo.Constants
import com.example.venyoo.venues.FourSquareVenueImageSchema
import com.example.venyoo.venues.FourSquareVenueResponseSchema
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("venues/search?radius=5&limit=1")
    suspend fun fetchFourSquareVenue(
        @Query("ll") latlng: String
    ): FourSquareVenueResponseSchema

    @GET("venues/{VENUE_ID}/photos?limit=20")
    suspend fun fetchFourSquarePhotos(
        @Path(value = "VENUE_ID", encoded = true) venueId: String
    ): FourSquareVenueImageSchema


}