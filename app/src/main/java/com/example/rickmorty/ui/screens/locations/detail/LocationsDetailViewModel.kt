package com.example.rickmorty.ui.screens.locations.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.dto.Location
import com.example.rickmorty.data.repository.LocationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationsDetailViewModel(
    private val locationsRepository: LocationsRepository,
    private val locationId: Int
) : ViewModel() {

    private val _locationState = MutableStateFlow<Location?>(null)
    val locationState: StateFlow<Location?> = _locationState.asStateFlow()

    init {
        fetchLocationById()
    }

    private fun fetchLocationById() {
        viewModelScope.launch {
            val location = locationsRepository.fetchLocationById(locationId)
            _locationState.value = location
        }
    }
}