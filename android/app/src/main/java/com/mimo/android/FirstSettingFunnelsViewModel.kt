package com.mimo.android

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FirstSettingFunnelsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FirstSettingFunnelsUiState())
    val uiState: StateFlow<FirstSettingFunnelsUiState> = _uiState.asStateFlow()

    fun init(currentStepId: Int){
        viewModelScope.launch {
            delay(1000)
            _uiState.update {
                prevState -> prevState.copy(currentStepId = currentStepId)
            }
        }
    }

    fun updateCurrentStep(stepId: Int){
        _uiState.update {
            prevState -> prevState.copy(currentStepId = stepId)
        }
    }

    fun setHubAndRedirect(qrCode: String){
        if (qrCode.isEmpty()) {
            return
        }

        viewModelScope.launch {
            delay(3000)
            // TODO: Loading UI State

            // TODO: 여기서 Spring server 통해서 허브(QR)이미 존재하는지 아닌지 확인하고 리다이렉트

            // FIXME: Case1 이미 집이 등록된 허브라서 메인으로 이동
            // TODO: 이미 집이 등록되어있기 때문에 이 유저를 집과허브(?)에 등록시켜야함. 아무튼 등록시켜야함
            _uiState.update {
                prevState -> prevState.copy(
                    currentStepId = R.string.first_setting_redirect_main_after_find_existing_hub,
                    hub = Hub(
                        qrCode = qrCode,
                        location = "경기도 고양시 일산서구 산현로 34",
                        locationAlias = "상윤이의 본가"
                    )
                )
            }

            // FIXME: Case2 새로운 허브라서 등록 화면으로 이동
            _uiState.update {
                      prevState -> prevState.copy(currentStepId = R.string.first_setting_redirect_location_register_after_find_new_hub)
            }
        }
    }

    fun redirectMain(){
        viewModelScope.launch {
            delay(3000)
            // TODO: Loading UI State

            _uiState.update {
                    prevState -> prevState.copy(currentStepId = null, hub = null)
            }
        }
    }

    fun redirectAutoRegisterLocationFunnel(){
        viewModelScope.launch {
            delay(3000)
            // TODO: Loading UI State

            _uiState.update {
                    prevState -> prevState.copy(currentStepId = R.string.first_setting_funnel_auto_register_location)
            }
        }
    }
}

data class FirstSettingFunnelsUiState (
    val currentStepId: Int? = null,
    val hub: Hub? = null,
    val userLocation: String? = null // TODO: MainActivity에서 위치정보 받아와서 초깃값으로 저장해야함
)

data class Hub(
    val qrCode: String?,
    val location: String?,
    val locationAlias: String?
)