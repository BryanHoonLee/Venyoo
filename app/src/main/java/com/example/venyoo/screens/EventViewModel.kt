package com.example.venyoo.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.example.venyoo.venues.SetlistResponse
import com.example.venyoo.venues.TicketMasterEventResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val repository: EventRepository
): SavedStateViewModel() {
    companion object{
        const val SAVED_STATE_HANDLE_EVENTS = "events"
        const val SAVED_STATE_HANDLE_CURRENT_EVENT = "current_event"
        const val SAVED_STATE_HANDLE_SETLIST = "setlist"

    }

    val venueEventList: LiveData<List<TicketMasterEventResponse>>
        get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_EVENTS)

    val currentEvent: LiveData<TicketMasterEventResponse>
        get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_CURRENT_EVENT)

    val setlist: LiveData<List<SetlistResponse>>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_SETLIST
        )

    fun fetchVenueEvents(venueId: String) {
        viewModelScope.launch {
            savedStateHandle[SAVED_STATE_HANDLE_EVENTS] =
                repository.fetchVenueEvents(venueId)
        }
    }

    fun updateCurrentEvent(event: TicketMasterEventResponse){
        savedStateHandle[SAVED_STATE_HANDLE_CURRENT_EVENT] = event
    }

    fun fetchSetlist(artistTicketMasterId: String){
        viewModelScope.launch {
            savedStateHandle[SAVED_STATE_HANDLE_SETLIST] = repository.fetchSetlist(artistTicketMasterId)
        }
    }
}