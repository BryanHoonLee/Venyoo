package com.example.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SetlistFMSetlistResponseSchema(
    val setlist: List<SetlistResponse> = emptyList()
) : Parcelable

@Parcelize
data class SetlistResponse(
    val artist: SetlistFMArtist = SetlistFMArtist(),
    val sets : SetListFMSets = SetListFMSets(),
    val tour: SetlistFMTour = SetlistFMTour(),
    val url: String = "",
    val eventDate: String = "",
    val lastUpdated: String = ""
    ) : Parcelable

@Parcelize
data class SetlistFMArtist(
    val mbid: String = "",
    val tmid: String = "",
    val name: String = "",
    val url: String = ""
) : Parcelable

@Parcelize
data class SetListFMSets(
    val set: List<SetListFMSet> = emptyList()
): Parcelable

@Parcelize
data class SetListFMSet(
    val name: String = "",
    val encore: String = "",
    val song: List<SetlistFMSong> = emptyList()
) : Parcelable

@Parcelize
data class SetlistFMSong(
    val name: String = "",
    val info: String = ""
) : Parcelable

@Parcelize
data class SetlistFMTour(
    val name: String = ""
) : Parcelable