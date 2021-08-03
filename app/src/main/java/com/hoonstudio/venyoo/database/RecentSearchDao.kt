package com.hoonstudio.venyoo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hoonstudio.venyoo.venues.RecentSearch
import com.hoonstudio.venyoo.venues.TicketMasterAttractionResponse

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM RecentSearch ORDER BY uId desc limit 10")
    fun fetchRecentSearch(): List<RecentSearch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentSearch(recentSearch: RecentSearch)
}