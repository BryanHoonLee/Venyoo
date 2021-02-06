package com.example.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueListResponseSchema (
        val _embedded: Embedded
): Parcelable

@Parcelize
data class VenueResponse(
        val id: String,
        val name: String,
        val description: String,
        val address: VenueAddressResponse,
        val city: VenueCityResponse,
        val state: VenueStateResponse,
        val postalCode: String,
        val location: VenueLocationResponse,
        val url: String,

        ): Parcelable

@Parcelize
data class VenueAddressResponse(
        val line1: String,
        val line2: String,
        val line3: String
): Parcelable

@Parcelize
data class VenueCityResponse(
        val name: String
): Parcelable

@Parcelize
data class VenueStateResponse(
        val stateCode: String,
        val name: String
): Parcelable

@Parcelize
data class VenueLocationResponse(
        val longitude: Float,
        val latitude: Float
): Parcelable