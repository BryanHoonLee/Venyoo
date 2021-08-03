package com.hoonstudio.venyoo.venues

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentSearch(
    @PrimaryKey(autoGenerate = true) var uId: Int = 0,
    @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "type") var type: String = ""
    )