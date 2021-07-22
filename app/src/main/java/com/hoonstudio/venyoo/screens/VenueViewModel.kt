package com.hoonstudio.venyoo.screens

import androidx.lifecycle.*
import com.hoonstudio.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.hoonstudio.venyoo.venues.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class VenueViewModel @Inject constructor(
    private val venueRepository: VenueRepository
) : SavedStateViewModel() {

    companion object {
        const val SAVED_STATE_HANDLE_VENUE_LIST = "venues"
        const val SAVED_STATE_HANDLE_CURRENT_VENUE = "current_venue"
        const val SAVED_STATE_HANDLE_ADDITIONAL_VENUE = "additional_venue_info"
        const val SAVED_STATE_HANDLE_ADDITIONAL_PHOTOS = "photos"
    }


    val venueList: LiveData<List<TicketMasterVenueResponse>>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_VENUE_LIST
        )

    val currentVenue: LiveData<TicketMasterVenueResponse>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_CURRENT_VENUE
        )
    val additionalCurrentVenueInfo: LiveData<List<FourSquareVenueResponse>>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_ADDITIONAL_VENUE
        )
    val additionalCurrentVenuePhotos: LiveData<List<FourSquareImageItem>>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_ADDITIONAL_PHOTOS
        )

    val imageList = MediatorLiveData<List<TicketMasterVenueImage>>()

    var imageListObserversAdded: Boolean = false

    fun fetchImages() {
        if (!imageListObserversAdded) {
            imageList.addSource(currentVenue) {
                imageList.value = combineImages(currentVenue, additionalCurrentVenuePhotos)
            }

            imageList.addSource(additionalCurrentVenuePhotos) {
                imageList.value = combineImages(currentVenue, additionalCurrentVenuePhotos)
            }
            imageListObserversAdded = true
        }
    }

    private fun combineImages(
        currentVenue: LiveData<TicketMasterVenueResponse>,
        additionalVenuePhotos: LiveData<List<FourSquareImageItem>>
    ): List<TicketMasterVenueImage> {
        val currentVenue = currentVenue.value
        if (currentVenue == null) {
            return emptyList()
        } else {
            val currentPhotos = currentVenue.images
            val additionalPhotos = additionalVenuePhotos.value

            if (currentPhotos == null || additionalPhotos == null) {
                return emptyList()
            }

            val transformFourSquareToTicketMasterImages =
                additionalPhotos.map { photo ->
                    TicketMasterVenueImage(
                        "${photo.prefix}${photo.width}x${photo.height}${photo.suffix}",
                        photo.width,
                        photo.height
                    )
                }

            val combinedList = currentPhotos + transformFourSquareToTicketMasterImages
            return combinedList
        }
    }

    fun updateCurrentVenue(venue: TicketMasterVenueResponse) {
        savedStateHandle[SAVED_STATE_HANDLE_CURRENT_VENUE] = venue
    }

    fun fetchVenues(venueName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val venues: MutableList<TicketMasterVenueResponse> =
                venueRepository.fetchVenues(venueName).toMutableList()
            savedStateHandle.getLiveData<List<TicketMasterVenueResponse>>(
                SAVED_STATE_HANDLE_VENUE_LIST
            ).postValue(venues.filter { response ->
                !response.images.isNullOrEmpty() || !response.social.twitter.handle.isNullOrEmpty() || !response.boxOfficeInfo.phoneNumberDetail.isNullOrEmpty()
            })
        }
    }

    fun fetchVenuesByCoordinates(latLong: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val venues: MutableList<TicketMasterVenueResponse> =
                venueRepository.fetchVenuesByCoordinates(latLong).toMutableList()
            savedStateHandle.getLiveData<List<TicketMasterVenueResponse>>(SAVED_STATE_HANDLE_VENUE_LIST).postValue(venues.filter { response ->
                !response.images.isNullOrEmpty() || !response.social.twitter.handle.isNullOrEmpty() || !response.boxOfficeInfo.phoneNumberDetail.isNullOrEmpty()
            })
        }
    }

    fun fetchFourSquareVenue(latlng: String) {
        viewModelScope.launch(Dispatchers.IO) {
            savedStateHandle.getLiveData<List<FourSquareVenueResponse>>(SAVED_STATE_HANDLE_ADDITIONAL_VENUE).postValue(
                venueRepository.fetchFourSquareVenue(latlng))
        }
    }

    fun fetchFourSquarePhotos(venueId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            savedStateHandle.getLiveData<List<FourSquareImageItem>>(SAVED_STATE_HANDLE_ADDITIONAL_PHOTOS).postValue(
                venueRepository.fetchFourSquarePhotos(venueId))
        }
    }
}