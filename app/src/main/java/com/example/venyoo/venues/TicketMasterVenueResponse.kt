package com.example.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketMasterVenueListResponseSchema (
        val _embedded: TicketMasterEmbedded
): Parcelable

@Parcelize
data class TicketMasterEmbedded (
        val venues: List<TicketMasterVenueResponse>
): Parcelable

@Parcelize
data class TicketMasterVenueResponse(
        val id: String = "",
        val name: String = "",
        val description: String = "",
        val address: TicketMasterVenueAddressResponse = TicketMasterVenueAddressResponse("","",""),
        val city: TicketMasterVenueCityResponse = TicketMasterVenueCityResponse(""),
        val state: TicketMasterVenueStateResponse = TicketMasterVenueStateResponse("",""),
        val postalCode: String = "",
        val location: TicketMasterVenueLocationResponse = TicketMasterVenueLocationResponse(0.0f, 0.0f),
        val url: String = (""),
        val additionalInfo: String = "",
        val images: List<TicketMasterVenueImage> = emptyList(),
        val distance: Double = 0.0,
        val social: TicketMasterSocial = TicketMasterSocial(TicketMasterTwitter("")),
        val boxOfficeInfo: TicketMasterBoxOfficeInfo = TicketMasterBoxOfficeInfo("", "", "")
        ): Parcelable

@Parcelize
data class TicketMasterVenueAddressResponse(
        val line1: String = "",
        val line2: String = "",
        val line3: String = ""
): Parcelable

@Parcelize
data class TicketMasterVenueCityResponse(
        val name: String = ""
): Parcelable

@Parcelize
data class TicketMasterVenueStateResponse(
        val stateCode: String = "",
        val name: String = ""
): Parcelable

@Parcelize
data class TicketMasterVenueLocationResponse(
        val longitude: Float = 0.0f,
        val latitude: Float = 0.0f
): Parcelable

@Parcelize
data class TicketMasterVenueImage(
        val url: String = "",
        val ratio: String = "",
        val width: Int = 0,
        val height: Int = 0,
        val fallback: Boolean = false,
        val attribution: String = ""
): Parcelable

@Parcelize
data class TicketMasterSocial(
        val twitter: TicketMasterTwitter = TicketMasterTwitter("")
): Parcelable

@Parcelize
data class TicketMasterTwitter(
        val handle: String = ""
): Parcelable

@Parcelize
data class TicketMasterBoxOfficeInfo(
        val phoneNumberDetail: String = "",
        val openHoursDetail: String = "",
        val acceptedPaymentDetail: String = ""
): Parcelable

