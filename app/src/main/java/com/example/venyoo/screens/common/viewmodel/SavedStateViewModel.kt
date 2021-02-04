package com.example.venyoo.screens.common.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

abstract class SavedStateViewModel: ViewModel() {
    lateinit var savedStateHandle: SavedStateHandle
}