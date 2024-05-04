package com.mimo.android.screens.firstsettingfunnels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mimo.android.FirstSettingFunnelsViewModel
import com.mimo.android.components.HeadingLarge
import com.mimo.android.QrCodeViewModel
import com.mimo.android.R
import com.mimo.android.components.Icon

@Composable
fun FirstSettingFunnelsRoot(
    qrCodeViewModel: QrCodeViewModel,
    firstSettingFunnelsViewModel: FirstSettingFunnelsViewModel,
    checkCameraPermission: (() -> Unit)? = null
){
    val firstSettingFunnelsUiState by firstSettingFunnelsViewModel.uiState.collectAsState()
    val qrCodeUiState by qrCodeViewModel.uiState.collectAsState()

    // FIXME: 이 Column은 Test용 입니다
    Column {
        Icon(imageVector = Icons.Filled.ArrowBack, onClick = {
            firstSettingFunnelsViewModel.updateCurrentStep(R.string.test_funnel)
        })

        if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_start_funnel) {
            FirstSettingStartFunnel(
                goNext = {
                    firstSettingFunnelsViewModel.updateCurrentStep(stepId = R.string.qr_code_scan_funnel)
                }
            )
            return
        }

        if (firstSettingFunnelsUiState.currentStepId == R.string.qr_code_scan_funnel) {
            QrCodeScanFunnel(
                goPrev = {
                    firstSettingFunnelsViewModel.updateCurrentStep(stepId = R.string.first_setting_start_funnel)
                },
                checkCameraPermission = checkCameraPermission
            )
            return
        }

        if (firstSettingFunnelsUiState.currentStepId == R.string.hub_find_waiting_funnel) {
            HubFindWaitingFunnel(
                goNext = {}
            )
            return
        }

        if (firstSettingFunnelsUiState.currentStepId == R.string.if_first_find_hub_then_go_main_funnel) {
            IfFirstFindHubThenGoMain(
                homeName = "상윤이의 본가",
                homeLocation = "경기도 고양시 일산서구 산현로 34",
                goNext = {}
            )
            return
        }

        if (firstSettingFunnelsUiState.currentStepId == R.string.if_find_new_hub_then_go_register_location_funnel) {
            IfFindNewHubThenGoRegisterLocation(
                goNext = {}
            )
            return
        }

        if (firstSettingFunnelsUiState.currentStepId == 5) {
            HeadingLarge(text = "5페이지..")
            return
        }

        if (firstSettingFunnelsUiState.currentStepId == R.string.test_funnel) {
            TestFunnel(
                firstSettingFunnelsViewModel = firstSettingFunnelsViewModel
            )
            return
        }
    }
}