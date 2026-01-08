package com.example.reviewed.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reviewed.data.model.LocationDto
import com.example.reviewed.data.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    private val repository = LocationRepository()

    private val _locations = MutableStateFlow<List<LocationDto>>(emptyList())
    val locations: StateFlow<List<LocationDto>> = _locations

    fun loadLocations() {
        viewModelScope.launch {
            try {
                _locations.value = repository.getLocations()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}