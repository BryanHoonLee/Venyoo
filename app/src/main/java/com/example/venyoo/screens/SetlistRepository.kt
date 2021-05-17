package com.example.venyoo.screens

import com.example.venyoo.networking.SetlistFMApi
import com.example.venyoo.venues.SetlistResponse
import javax.inject.Inject

class SetlistRepository @Inject constructor(private val setlistFMApi: SetlistFMApi) {
    suspend fun fetchSetlist(artistTicketMasterId: String): List<SetlistResponse>{
        return setlistFMApi.fetchSetList(artistTicketMasterId).setlist
    }
}