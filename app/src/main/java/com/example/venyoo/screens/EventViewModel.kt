package com.example.venyoo.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.example.venyoo.venues.TicketMasterEventResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val repository: EventRepository
): SavedStateViewModel() {
    companion object{
        const val SAVED_STATE_HANDLE_EVENTS = "events"
    }

    val venueEventList: LiveData<List<TicketMasterEventResponse>>
        get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_EVENTS)

    fun fetchVenueEvents(venueId: String) {
        viewModelScope.launch {
            savedStateHandle[SAVED_STATE_HANDLE_EVENTS] =
                repository.fetchVenueEvents(venueId)
        }
    }
}