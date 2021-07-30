package com.hoonstudio.venyoo.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hoonstudio.venyoo.Constants
import com.hoonstudio.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.hoonstudio.venyoo.venues.SetlistResponse
import com.hoonstudio.venyoo.venues.TicketMasterAttractionResponse
import com.hoonstudio.venyoo.venues.TicketMasterEventResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val repository: EventRepository
): SavedStateViewModel() {
    companion object{
        const val SAVED_STATE_HANDLE_EVENTS = "events"
        const val SAVED_STATE_HANDLE_CURRENT_EVENT = "current_event"
        const val SAVED_STATE_HANDLE_SETLIST = "setlist"
        const val SAVED_STATE_HANDLE_ATTRACTIONS = "attractions"
        const val SAVED_STATE_HANDLE_CURRENT_ATTRACTION = "current_attraction"
    }

    val venueEventList: LiveData<List<TicketMasterEventResponse>>
        get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_EVENTS)

    val currentEvent: LiveData<TicketMasterEventResponse>
        get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_CURRENT_EVENT)

    var currentAddress = ""

    val setlist: LiveData<List<SetlistResponse>>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_SETLIST
        )

    fun fetchVenueEvents(venueId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            savedStateHandle.getLiveData<List<TicketMasterEventResponse>>(SAVED_STATE_HANDLE_EVENTS).postValue(repository.fetchVenueEvents(venueId))
        }
    }

    fun updateCurrentEvent(event: TicketMasterEventResponse){
        savedStateHandle[SAVED_STATE_HANDLE_CURRENT_EVENT] = event
    }



    fun fetchSetlist(artistName: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(artistName.equals(Constants.NULL_OR_EMPTY_VALUE)){
                    savedStateHandle.getLiveData<List<SetlistResponse>>(SAVED_STATE_HANDLE_SETLIST).postValue(emptyList())
                }else{
                    savedStateHandle.getLiveData<List<SetlistResponse>>(SAVED_STATE_HANDLE_SETLIST).postValue(repository.fetchSetlist(artistName))
                }
            }catch (httpException: HttpException){
                savedStateHandle.getLiveData<List<SetlistResponse>>(SAVED_STATE_HANDLE_SETLIST).postValue(emptyList())
                Log.e("EventViewModel", httpException.message())
            }
        }
    }


    fun updateCurrentAddress(address: String){
        currentAddress = address
    }
}