package com.mimo.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FirstSettingFunnelsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FirstSettingFunnelsUiState())
    val uiState: StateFlow<FirstSettingFunnelsUiState> = _uiState.asStateFlow()

    fun init(currentStepId: Int){
        viewModelScope.launch {
            delay(1000)
            _uiState.value = FirstSettingFunnelsUiState(currentStepId = currentStepId)
        }
    }

    fun updateCurrentStep(stepId: Int){
        _uiState.value = FirstSettingFunnelsUiState(currentStepId = stepId)
    }
}

data class FirstSettingFunnelsUiState (
    val currentStepId: Int? = null
)