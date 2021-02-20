package com.example.venyoo.screens.common.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.venyoo.screens.VenueListViewModel
import com.example.venyoo.screens.VenueViewModel
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val venueViewModelProvider: Provider<VenueViewModel>,
    private val venueListViewModelProvider: Provider<VenueListViewModel>,
    savedStateRegistryOwner: SavedStateRegistryOwner
): AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        val viewModel = when(modelClass){
            VenueViewModel::class.java -> venueViewModelProvider.get() as T
            VenueListViewModel::class.java -> venueListViewModelProvider.get() as T
            else -> throw RuntimeException("unsupported viewmodel: $modelClass")
        }

        if(viewModel is SavedStateViewModel){
            viewModel.savedStateHandle = handle
        }

        return viewModel
    }
}