package com.example.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Embedded (
        val venues: List<VenueResponse>
        ): Parcelable