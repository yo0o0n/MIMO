package com.mimo.android.screens.firstsettingfunnels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mimo.android.FirstSettingFunnelsViewModel
import com.mimo.android.components.HeadingLarge
import com.mimo.android.QrCodeViewModel
import com.mimo.android.R

@Composable
fun FirstSettingFunnelsRoot(
    qrCodeViewModel: QrCodeViewModel,
    firstSettingFunnelsViewModel: FirstSettingFunnelsViewModel,
    checkCameraPermission: (() -> Unit)? = null
){
    val firstSettingFunnelsUiState by firstSettingFunnelsViewModel.uiState.collectAsState()
    val qrCodeUiState by qrCodeViewModel.uiState.collectAsState()

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_start_funnel) {
        FirstSettingStartFunnel(
            goNext = {
                firstSettingFunnelsViewModel.updateCurrentStep(stepId = R.string.qr_code_scan_funnel)
            }
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.qr_code_scan_funnel || qrCodeUiState.qrCode == null) {
        QrCodeScanFunnel(
            goPrev = {
                firstSettingFunnelsViewModel.updateCurrentStep(stepId = R.string.first_setting_start_funnel)
            },
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.if_first_find_hub_then_go_main_funnel) {
        HeadingLarge(text = "상윤이의 본가 ${qrCodeUiState.qrCode}")
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.if_find_new_hub_then_go_register_location_funnel) {
        HeadingLarge(text = "새로운 허브를 발견했어요! ${qrCodeUiState.qrCode}")
    }

    if (firstSettingFunnelsUiState.currentStepId == 5) {
        HeadingLarge(text = "5페이지..")
    }
}