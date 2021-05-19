package com.example.venyoo.networking

import com.example.venyoo.venues.SetlistFMSetlistResponseSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface SetlistFMApi {

    @GET("search/setlists?p=1&sort=relevance")
    suspend fun fetchSetList(
        @Query("artistName") artistName: String
    ): SetlistFMSetlistResponseSchema


}