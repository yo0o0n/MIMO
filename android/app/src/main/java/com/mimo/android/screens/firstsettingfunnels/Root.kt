package com.mimo.android.screens.firstsettingfunnels

import android.util.Log
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

    FunnelMatcher(
        qrCodeViewModel = qrCodeViewModel,
        firstSettingFunnelsViewModel = firstSettingFunnelsViewModel,
        checkCameraPermission = checkCameraPermission
    )

//    TestFunnelWrapper(
//        firstSettingFunnelsViewModel = firstSettingFunnelsViewModel
//    ) {
//        FunnelMatcher(
//            qrCodeViewModel = qrCodeViewModel,
//            firstSettingFunnelsViewModel = firstSettingFunnelsViewModel,
//            checkCameraPermission = checkCameraPermission
//        )
//    }
}

@Composable
fun FunnelMatcher(
    qrCodeViewModel: QrCodeViewModel,
    firstSettingFunnelsViewModel: FirstSettingFunnelsViewModel,
    checkCameraPermission: () -> Unit
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
            goNext = {
                val qrCode = qrCodeUiState.qrCode

                // TODO: 이건 로직상 에러 상황임.. 이 상황이 발생하면 비상...
                if (qrCode == null) {
                    println("QR CODE 없음...")
                    return@HubFindWaitingFunnel
                }
                firstSettingFunnelsViewModel.setHubAndRedirect(qrCode)
            }
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.redirect_main_after_find_existing_hub) {
        RedirectMainAfterFindExistingHub(
            homeName = firstSettingFunnelsUiState.hub?.locationAlias as String,
            homeLocation = firstSettingFunnelsUiState.hub?.location as String,
            goNext = {
                firstSettingFunnelsViewModel.redirectMain()
            }
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.redirect_location_register_after_find_new_hub) {
        RedirectLocationRegisterAfterFindNewHub(
            goNext = {
                firstSettingFunnelsViewModel.redirectAutoRegisterLocationFunnel()
            }
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