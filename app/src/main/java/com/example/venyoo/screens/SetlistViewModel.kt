package com.example.venyoo.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.example.venyoo.venues.SetlistResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class SetlistViewModel @Inject constructor(
    private val setlistRepository: SetlistRepository
) : SavedStateViewModel() {

    companion object {
        const val SAVED_STATE_HANDLE_SETLIST = "setlist"
    }

    val setlist: LiveData<List<SetlistResponse>>
        get() = savedStateHandle.getLiveData(
            SAVED_STATE_HANDLE_SETLIST
        )

    fun fetchSetlist(artistTicketMasterId: String){
        viewModelScope.launch {
            savedStateHandle[SAVED_STATE_HANDLE_SETLIST] = setlistRepository.fetchSetlist(artistTicketMasterId)
        }
    }
}