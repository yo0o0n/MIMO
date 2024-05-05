package com.mimo.android.screens.firstsettingfunnels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mimo.android.FirstSettingFunnelsViewModel
import com.mimo.android.QrCodeViewModel
import com.mimo.android.R
import com.mimo.android.components.Icon

@Composable
fun FirstSettingFunnelsRoot(
    qrCodeViewModel: QrCodeViewModel,
    firstSettingFunnelsViewModel: FirstSettingFunnelsViewModel,
    checkCameraPermission: () -> Unit
){

    val qrCodeUiState by qrCodeViewModel.uiState.collectAsState()

    TestFunnelWrapper(
        firstSettingFunnelsViewModel = firstSettingFunnelsViewModel
    ) {
        FunnelMatcher(
            qrCodeViewModel = qrCodeViewModel,
            firstSettingFunnelsViewModel = firstSettingFunnelsViewModel,
            checkCameraPermission = checkCameraPermission
        )
    }
}

@Composable
fun FunnelMatcher(
    qrCodeViewModel: QrCodeViewModel,
    firstSettingFunnelsViewModel: FirstSettingFunnelsViewModel,
    checkCameraPermission: () -> Unit
){
    val firstSettingFunnelsUiState by firstSettingFunnelsViewModel.uiState.collectAsState()

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

    if (firstSettingFunnelsUiState.currentStepId == R.string.redirect_main_after_find_existing_hub) {
        RedirectMainAfterFindExistingHub(
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

    if (firstSettingFunnelsUiState.currentStepId == R.string.auto_register_location_funnel) {
        AutoRegisterLocationFunnel(
            location = "서울특별시 강남구 테헤란로 212",
            onDirectlyEnterLocation = {},
            onConfirm = {}
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.make_location_alias_funnel){
        MakeLocationAliasFunnel(
            location = "서울특별시 강남구 테헤란로 212",
            goPrev = {},
            onComplete = {}
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.check_entered_hub_info_funnel) {
        CheckEnteredHubInfoFunnel(
            location = "서울특별시 강남구 테헤란로 212",
            locationAlias = "서울특별시 집",
            goPrev = {},
            goStartScreen = {},
            onConfirm = {}
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.redirect_main_after_register_hub_and_location) {
        RedirectMainAfterRegisterHubAndLocation(
            locationAlias = "서울특별시 집",
            location = "서울특별시 강남구 테헤란로 212",
            goNext = {}
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.test_funnel) {
        TestFunnel(
            firstSettingFunnelsViewModel = firstSettingFunnelsViewModel
        )
        return
    }
}

@Composable
fun TestFunnelWrapper(
    firstSettingFunnelsViewModel: FirstSettingFunnelsViewModel,
    children: @Composable () -> Unit
){
    Column {
        Icon(imageVector = Icons.Filled.Home, onClick = {
            firstSettingFunnelsViewModel.updateCurrentStep(R.string.test_funnel)
        })

        Spacer(modifier = Modifier.padding(30.dp))

        children()
    }
}