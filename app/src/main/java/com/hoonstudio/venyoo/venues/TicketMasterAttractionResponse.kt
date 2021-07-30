package com.hoonstudio.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketMasterAttractionSchema(
    val _embedded: TicketMasterAttractionEmbedded = TicketMasterAttractionEmbedded()
): Parcelable


@Parcelize
data class TicketMasterAttractionEmbedded(
    val attractions: List<TicketMasterAttractionResponse> = emptyList()
): Parcelable

@Parcelize
class TicketMasterAttractionResponse(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val url: String = "",
    val images: List<TicketMasterAttractionImage> = emptyList(),
): Parcelable

@Parcelize
data class TicketMasterAttractionImage(
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0
): Parcelable