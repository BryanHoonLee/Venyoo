package com.hoonstudio.venyoo.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hoonstudio.venyoo.screens.common.viewmodel.SavedStateViewModel
import com.hoonstudio.venyoo.venues.RecentSearch
import com.hoonstudio.venyoo.venues.TicketMasterAttractionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AttractionViewModel @Inject constructor(
    private val repository: AttractionRepository
): SavedStateViewModel() {

    companion object{
        const val SAVED_STATE_HANDLE_ATTRACTIONS = "attractions"
        const val SAVED_STATE_HANDLE_CURRENT_ATTRACTION = "current_attraction"
        const val SAVED_STATE_HANDLE_RECENT_SEARCH = "recent_search"
    }


    val attractions: LiveData<List<TicketMasterAttractionResponse>>
        get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_ATTRACTIONS)

    val currentAttraction: LiveData<TicketMasterAttractionResponse>
        get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_CURRENT_ATTRACTION)

    val recentSearch: LiveData<List<RecentSearch>>
        get() = savedStateHandle.getLiveData(SAVED_STATE_HANDLE_RECENT_SEARCH)

    fun fetchAttractions(attractionName: String){
        viewModelScope.launch(Dispatchers.IO) {
            savedStateHandle.getLiveData<List<TicketMasterAttractionResponse>>(
                SAVED_STATE_HANDLE_ATTRACTIONS
            ).postValue(repository.fetchAttractions(attractionName))
        }
    }

    fun fetchRecentSearch(){
        viewModelScope.launch(Dispatchers.IO){
            savedStateHandle.getLiveData<List<RecentSearch>>(
                SAVED_STATE_HANDLE_RECENT_SEARCH
            ).postValue(repository.fetchRecentSearch())
        }
    }

    fun insertAttraction(recentSearch: RecentSearch){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertRecentSearch(recentSearch)
        }
    }

    fun updateCurrentAttraction(attraction: TicketMasterAttractionResponse) {
        savedStateHandle[SAVED_STATE_HANDLE_CURRENT_ATTRACTION] = attraction
    }
}