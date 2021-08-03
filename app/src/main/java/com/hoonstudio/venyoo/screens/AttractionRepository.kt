package com.hoonstudio.venyoo.screens

import com.hoonstudio.venyoo.database.RecentSearchDao
import com.hoonstudio.venyoo.networking.TicketMasterApi
import com.hoonstudio.venyoo.venues.RecentSearch
import com.hoonstudio.venyoo.venues.TicketMasterAttractionResponse
import javax.inject.Inject

class AttractionRepository @Inject constructor(private val ticketMasterApi: TicketMasterApi, private val recentSearchDao: RecentSearchDao) {

    suspend fun fetchAttractions(attractionName: String): List<TicketMasterAttractionResponse>{
        return ticketMasterApi.fetchAttractions(attractionName)._embedded.attractions
    }

    fun insertRecentSearch(recentSearch: RecentSearch){
        recentSearchDao.insertRecentSearch(recentSearch)
    }

    fun fetchRecentSearch(): List<RecentSearch>{
        return recentSearchDao.fetchRecentSearch()
    }
}