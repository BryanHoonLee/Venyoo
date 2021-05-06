package com.example.venyoo.venues

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketMasterEventSchema(
    val _embedded: TicketMasterEventEmbedded = TicketMasterEventEmbedded()
):Parcelable

@Parcelize
data class TicketMasterEventEmbedded(
    val events: List<TicketMasterEventResponse> = emptyList()
): Parcelable

@Parcelize
data class TicketMasterEventResponse(
    val _embedded: TicketMasterEventAttractionEmbedded = TicketMasterEventAttractionEmbedded(),
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val additionalInfo: String = "",
    val location: TicketMasterEventLocation = TicketMasterEventLocation(),
    val distance: Double = 0.0,
    val images: List<TicketMasterEventImage> = emptyList(),
    val dates: TicketMasterEventDates = TicketMasterEventDates(),
    val sales: TicketMasterEventSales = TicketMasterEventSales(),
    val seatmap: SeatMap = SeatMap(),
    val info: String = "",
    val pleaseNote: String = ""
): Parcelable

@Parcelize
data class SeatMap(
    val staticUrl: String = ""
): Parcelable
@Parcelize
data class TicketMasterEventAttractionEmbedded(
    val attractions: List<TicketMasterEventAttraction> = emptyList()
): Parcelable

@Parcelize
data class TicketMasterEventAttraction(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val additionalInfo: String = "",
    val url: String = "",
    val images: List<TicketMasterEventImage> = emptyList()
): Parcelable

@Parcelize
data class TicketMasterEventLocation(
    val latitude: Float = 0.0f,
    val longitude: Float = 0.0f
): Parcelable

@Parcelize
data class TicketMasterEventImage(
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0
): Parcelable

@Parcelize
data class TicketMasterEventDates(
    val start: TicketMasterEventDateStart = TicketMasterEventDateStart(),
    val status: TicketMasterEventDateStatus = TicketMasterEventDateStatus()
): Parcelable

@Parcelize
data class TicketMasterEventDateStart(
    val localDate: String = "",
    val dateTime: String = "",
    val dateTBA: Boolean = false,
    val noSpecificTime: Boolean = false
): Parcelable

@Parcelize
data class TicketMasterEventDateStatus(
    val code: TicketMasterEventDateStatusCode = TicketMasterEventDateStatusCode.postponed
): Parcelable


enum class TicketMasterEventDateStatusCode(val codeName: String){
    onsale("Onsale"), offsale("Offsale"), cancelled("Cancelled"), postponed("Postponed"), rescheduled("Rescheduled")
}

@Parcelize
data class TicketMasterEventSales(
    val public: TicketMasterEventSalesPublic = TicketMasterEventSalesPublic(),
    val presales: List<TicketMasterEventSalesPresales> = emptyList()
): Parcelable

@Parcelize
data class TicketMasterEventSalesPublic(
    val startDateTime: String = "",
    val startTBD: Boolean = false
): Parcelable

@Parcelize
data class TicketMasterEventSalesPresales(
    val startDateTime: String = "",
    val url: String = ""
): Parcelable

data class TicketMasterConvertedLocalData(
    val month: String = "N/A",
    val day: String = "",
    val year: String = ""
)