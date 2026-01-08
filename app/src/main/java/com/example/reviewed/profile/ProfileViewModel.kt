package com.example.reviewed.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = true,
    val user: UserProfile? = null
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            delay(1800) // simulate API
            _uiState.value = ProfileUiState(
                isLoading = false,
                user = UserProfile(
                    id = "1",
                    name = "Isaac Koros",
                    phone = "+254 7XX XXX XXX",
                    imageUrl = null,
                    isProvider = true,
                    rating = 4.8,
                    jobsCompleted = 126
                )
            )
        }
    }
}
