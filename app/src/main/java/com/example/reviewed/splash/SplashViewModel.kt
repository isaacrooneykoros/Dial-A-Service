package com.example.reviewed.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        startSplash()
    }

    private fun startSplash() {
        viewModelScope.launch {
            delay(2000) // 2 seconds splash duration

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                navigateToHome = true
            )
        }
    }
}