package com.hoonstudio.venyoo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hoonstudio.venyoo.database.RecentSearchDao
import com.hoonstudio.venyoo.dependencyinjection.app.AppScope
import com.hoonstudio.venyoo.venues.RecentSearch
import com.hoonstudio.venyoo.venues.TicketMasterAttractionResponse

@AppScope
@Database(entities = arrayOf(RecentSearch::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao

}