package com.example.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VenueListResponseSchema (
        val _embedded: Embedded
): Parcelable

@Parcelize
data class Embedded (
        val venues: List<VenueResponse>
): Parcelable

@Parcelize
data class VenueResponse(
        val id: String = "",
        val name: String = "",
        val description: String = "",
        val address: VenueAddressResponse = VenueAddressResponse("","",""),
        val city: VenueCityResponse = VenueCityResponse(""),
        val state: VenueStateResponse = VenueStateResponse("",""),
        val postalCode: String = "",
        val location: VenueLocationResponse = VenueLocationResponse(0.0f, 0.0f),
        val url: String = (""),
        val additionalInfo: String = "",
        val images: List<VenueImage> = emptyList(),
        val distance: Double = 0.0
        ): Parcelable

@Parcelize
data class VenueAddressResponse(
        val line1: String = "",
        val line2: String = "",
        val line3: String = ""
): Parcelable

@Parcelize
data class VenueCityResponse(
        val name: String = ""
): Parcelable

@Parcelize
data class VenueStateResponse(
        val stateCode: String = "",
        val name: String = ""
): Parcelable

@Parcelize
data class VenueLocationResponse(
        val longitude: Float = 0.0f,
        val latitude: Float = 0.0f
): Parcelable

@Parcelize
data class VenueImage(
        val url: String = "",
        val ratio: String = "",
        val width: Int = 0,
        val height: Int = 0,
        val fallback: Boolean = false,
        val attribution: String = ""
): Parcelable

