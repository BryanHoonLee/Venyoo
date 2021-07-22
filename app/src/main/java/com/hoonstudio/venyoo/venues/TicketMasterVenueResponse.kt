        package com.hoonstudio.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketMasterVenueSchema (
        val _embedded: TicketMasterVenueEmbedded = TicketMasterVenueEmbedded()
): Parcelable

@Parcelize
data class TicketMasterVenueEmbedded (
        val venues: List<TicketMasterVenueResponse> = emptyList()
): Parcelable

@Parcelize
data class TicketMasterVenueResponse(
        val id: String = "",
        val name: String = "",
        val description: String = "",
        val address: TicketMasterVenueAddress = TicketMasterVenueAddress("","",""),
        val city: TicketMasterVenueCity = TicketMasterVenueCity(""),
        val state: TicketMasterVenueState = TicketMasterVenueState("",""),
        val postalCode: String = "",
        val location: TicketMasterVenueLocation = TicketMasterVenueLocation(0.0f, 0.0f),
        val url: String = (""),
        val additionalInfo: String = "",
        val images: List<TicketMasterVenueImage> = emptyList(),
        val distance: Double = 0.0,
        val social: TicketMasterSocial = TicketMasterSocial(TicketMasterTwitter("")),
        val boxOfficeInfo: TicketMasterBoxOfficeInfo = TicketMasterBoxOfficeInfo("", "", "")
        ): Parcelable

@Parcelize
data class TicketMasterVenueAddress(
        val line1: String = "",
        val line2: String = "",
        val line3: String = ""
): Parcelable

@Parcelize
data class TicketMasterVenueCity(
        val name: String = ""
): Parcelable

@Parcelize
data class TicketMasterVenueState(
        val stateCode: String = "",
        val name: String = ""
): Parcelable

@Parcelize
data class TicketMasterVenueLocation(
        val longitude: Float = 0.0f,
        val latitude: Float = 0.0f
): Parcelable

@Parcelize
data class TicketMasterVenueImage(
        val url: String = "",
        val width: Int = 0,
        val height: Int = 0
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

