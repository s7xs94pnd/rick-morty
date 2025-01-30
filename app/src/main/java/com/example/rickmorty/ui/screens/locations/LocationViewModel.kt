package com.example.rickmorty.ui.screens.locations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rickmorty.data.remote.dto.Location
import com.example.rickmorty.data.repository.LocationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocationViewModel(private val locationsRepository: LocationsRepository) : ViewModel() {

    private val _locationState = MutableSharedFlow<PagingData<Location>>()
    val locationState: SharedFlow<PagingData<Location>> = _locationState.asSharedFlow()

    fun fetchAllLocation() {
        viewModelScope.launch(Dispatchers.IO) {
                locationsRepository.fetchAllLocations()
                    .flow
                    .collectLatest {pagingData->
                        _locationState.emit( pagingData)
                    }
        }
    }
}