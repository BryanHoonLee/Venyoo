package com.example.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FourSquareVenueImageResponseObject(
    val response: FourSquareImageResponse = FourSquareImageResponse()
): Parcelable

@Parcelize
data class FourSquareImageResponse(
    val photos: FourSquareImagePhotos = FourSquareImagePhotos()
): Parcelable

@Parcelize
data class FourSquareImagePhotos(
    val items: List<FourSquareImageItem> = emptyList()
): Parcelable


@Parcelize
data class FourSquareImageItem(
    val id: String = "",
    val prefix: String = "",
    val suffix: String = "",
    val width: Int = 0,
    val height: Int = 0
): Parcelable