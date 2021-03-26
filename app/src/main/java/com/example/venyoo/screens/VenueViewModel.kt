package com.example.venyoo.screens

import android.util.Log
import androidx.lifecycle.*
import com.example.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.example.venyoo.venues.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class VenueViewModel @Inject constructor(
    private val venueRepository: VenueRepository
) : SavedStateViewModel() {

    companion object {
        const val SAVED_STATE_HANDLE_VENUE_LIST = "venues"
        const val SAVED_STATE_HANDLE_VENUE = "venue"
        const val SAVED_STATE_HANDLE_EVENTS = "events"
        const val SAVED_STATE_HANDLE_ADDITIONAL_VENUE = "additional_venue_info"
        const val SAVED_STATE_HANDLE_IMAGES = "images"
        const val SAVED_STATE_HANDLE_ADDITIONAL_PHOTOS = "photos"
    }


    val venueList: LiveData<List<TicketMasterVenueResponse>>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_VENUE_LIST
        )

    val currentVenue: LiveData<TicketMasterVenueResponse>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_VENUE
        )
    val additionalCurrentVenueInfo: LiveData<List<FourSquareVenueResponse>>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_ADDITIONAL_VENUE
        )
    val additionalCurrentVenuePhotos: LiveData<List<FourSquareImageItem>>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_ADDITIONAL_PHOTOS
        )
//    val venueImageList: LiveData<List<TicketMasterVenueImage>>
//        get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_IMAGES)



    val imageList = MediatorLiveData<List<TicketMasterVenueImage>>()

    var imageListObserversAdded: Boolean = false

     fun fetchImages() {
        if(!imageListObserversAdded) {
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
        if(currentVenue == null){
            return emptyList()
        }else {
            val currentPhotos = currentVenue.images
            val additionalPhotos = additionalVenuePhotos.value

            if(currentPhotos == null || additionalPhotos == null){
                return emptyList()
            }

            val transformFourSquareToTicketMasterImages =
                additionalPhotos.map { photo ->
                    TicketMasterVenueImage(
                        "${photo.prefix}${photo.width}x${photo.height}${photo.suffix}", photo.width, photo.height
                    )
                }

            val combinedList =  currentPhotos  + transformFourSquareToTicketMasterImages
            return combinedList
        }
    }

    fun updateCurrentVenue(venue: TicketMasterVenueResponse) {
        savedStateHandle[SAVED_STATE_HANDLE_VENUE] = venue
    }

    fun fetchVenues(venueName: String) {
        viewModelScope.launch {
            val venues: MutableList<TicketMasterVenueResponse> =
                venueRepository.fetchVenues(venueName).toMutableList()
            savedStateHandle[SAVED_STATE_HANDLE_VENUE_LIST] = venues.filter { response ->
                !response.images.isNullOrEmpty() || !response.social.twitter.handle.isNullOrEmpty() || !response.boxOfficeInfo.phoneNumberDetail.isNullOrEmpty()
            }
        }
    }

    fun fetchVenuesByCoordinates(latLong: String) {
        viewModelScope.launch {
            val venues: MutableList<TicketMasterVenueResponse> =
                venueRepository.fetchVenuesByCoordinates(latLong).toMutableList()
            savedStateHandle[SAVED_STATE_HANDLE_VENUE_LIST] = venues.filter { response ->
                !response.images.isNullOrEmpty() || !response.social.twitter.handle.isNullOrEmpty() || !response.boxOfficeInfo.phoneNumberDetail.isNullOrEmpty()
            }
        }
    }

    fun fetchFourSquareVenue(latlng: String) {
        viewModelScope.launch {
            savedStateHandle[SAVED_STATE_HANDLE_ADDITIONAL_VENUE] =
                venueRepository.fetchFourSquareVenue(latlng)
        }
    }

    fun fetchFourSquarePhotos(venueId: String) {
        viewModelScope.launch {
            savedStateHandle[SAVED_STATE_HANDLE_ADDITIONAL_PHOTOS] =
                venueRepository.fetchFourSquarePhotos(venueId)
        }
    }
}