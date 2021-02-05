package com.example.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueListResponseSchema (
        val _embedded: Embedded
        ): Parcelable