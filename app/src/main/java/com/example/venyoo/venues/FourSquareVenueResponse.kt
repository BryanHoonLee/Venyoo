package com.example.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FourSquareVenueResponseSchema(
    val response: FourSquareResponse = FourSquareResponse()
): Parcelable

@Parcelize
data class FourSquareResponse(
    val venues: List<FourSquareVenueResponse> = emptyList()
): Parcelable

@Parcelize
data class FourSquareVenueResponse(
    val id: String = "",
    val name: String = "",
    val location: FourSquareLocationResponse = FourSquareLocationResponse(),
    val categories: List<FourSquareCategories> = emptyList()
): Parcelable

@Parcelize
data class FourSquareLocationResponse(
    val address: String = "",
    val distance: Float = 0.0f,
    val postalCode: String = "",
    val cc: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val lat: Float = 0.0f,
    val lng: Float = 0.0f
): Parcelable

@Parcelize
data class FourSquareCategories(
    val id: String = "",
    val name: String = "",
    val icon: FourSquareIconResponse = FourSquareIconResponse()
): Parcelable

@Parcelize
data class FourSquareIconResponse(
    val prefix: String = "",
    val suffix: String = ""
): Parcelable

