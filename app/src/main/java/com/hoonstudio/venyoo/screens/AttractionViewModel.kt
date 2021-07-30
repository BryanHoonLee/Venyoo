package com.hoonstudio.venyoo.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hoonstudio.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.hoonstudio.venyoo.venues.TicketMasterAttractionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AttractionViewModel @Inject constructor(
    private val repository: EventRepository
): SavedStateViewModel() {

    companion object{
        const val SAVED_STATE_HANDLE_ATTRACTIONS = "attractions"
        const val SAVED_STATE_HANDLE_CURRENT_ATTRACTION = "current_attraction"
    }


    val attractions: LiveData<List<TicketMasterAttractionResponse>>
        get() = savedStateHandle.getLiveData(EventViewModel.SAVED_STATE_HANDLE_ATTRACTIONS)

    val currentAttraction: LiveData<TicketMasterAttractionResponse>
        get() = savedStateHandle.getLiveData(EventViewModel.SAVED_STATE_HANDLE_CURRENT_ATTRACTION)

    fun fetchAttractions(attractionName: String){
        viewModelScope.launch(Dispatchers.IO) {
            savedStateHandle.getLiveData<List<TicketMasterAttractionResponse>>(
                EventViewModel.SAVED_STATE_HANDLE_ATTRACTIONS
            ).postValue(repository.fetchAttractions(attractionName))
        }
    }

    fun updateCurrentAttraction(attraction: TicketMasterAttractionResponse) {
        savedStateHandle[EventViewModel.SAVED_STATE_HANDLE_CURRENT_ATTRACTION] = attraction
    }
}