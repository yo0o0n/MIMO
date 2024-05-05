package com.mimo.android.screens.firstsettingfunnels

import android.content.Context
import android.widget.Toast
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
    checkCameraPermission: () -> Unit,
    context: Context
){

//    FunnelMatcher(
//        qrCodeViewModel = qrCodeViewModel,
//        firstSettingFunnelsViewModel = firstSettingFunnelsViewModel,
//        checkCameraPermission = checkCameraPermission
//    )

    TestFunnelWrapper(
        firstSettingFunnelsViewModel = firstSettingFunnelsViewModel
    ) {
        FunnelMatcher(
            qrCodeViewModel = qrCodeViewModel,
            firstSettingFunnelsViewModel = firstSettingFunnelsViewModel,
            checkCameraPermission = checkCameraPermission,
            context = context
        )
    }
}

@Composable
fun FunnelMatcher(
    qrCodeViewModel: QrCodeViewModel,
    firstSettingFunnelsViewModel: FirstSettingFunnelsViewModel,
    checkCameraPermission: () -> Unit,
    context: Context
){
    val firstSettingFunnelsUiState by firstSettingFunnelsViewModel.uiState.collectAsState()
    val qrCodeUiState by qrCodeViewModel.uiState.collectAsState()

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_funnel_first_setting_start) {
        FunnelFirstSettingStart(
            goNext = {
                firstSettingFunnelsViewModel.updateCurrentStep(stepId = R.string.first_setting_funnel_qr_code_scan)
            }
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_funnel_qr_code_scan) {
        FunnelQrCodeScan(
            goPrev = {
                firstSettingFunnelsViewModel.updateCurrentStep(stepId = R.string.first_setting_funnel_first_setting_start)
            },
            checkCameraPermission = checkCameraPermission
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_funnel_hub_find_waiting) {
        FunnelHubFindWaiting(
            goNext = {
                val qrCode = qrCodeUiState.qrCode

                // TODO: 이건 로직상 에러 상황임.. 이 상황이 발생하면 비상...
                if (qrCode == null) {
                    println("QR CODE 없음...")
                    return@FunnelHubFindWaiting
                }
                firstSettingFunnelsViewModel.setHubAndRedirect(qrCode)
            }
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_redirect_main_after_find_existing_hub) {
        RedirectMainAfterFindExistingHub(
            hub = firstSettingFunnelsUiState.hub,
            goNext = {
                firstSettingFunnelsViewModel.redirectMain()
            },
            redirectAfterCatchError = {
                Toast.makeText(
                    context,
                    "다시 시도해주세요",
                    Toast.LENGTH_SHORT
                ).show()
                firstSettingFunnelsViewModel.updateCurrentStep(stepId = R.string.first_setting_funnel_qr_code_scan)
            }
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_redirect_location_register_after_find_new_hub) {
        RedirectLocationRegisterAfterFindNewHub(
            goNext = {
                firstSettingFunnelsViewModel.redirectAutoRegisterLocationFunnel()
            }
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_funnel_auto_register_location) {
        // TODO: manage state...
        FunnelAutoRegisterLocation(
            location = "서울특별시 강남구 테헤란로 212",
            onDirectlyEnterLocation = {
                  firstSettingFunnelsViewModel.updateCurrentStep(R.string.first_setting_funnel_enter_location_to_register_hub)
            },
            onConfirm = {}
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_funnel_make_location_alias){
        // TODO: manage state...
        FunnelMakeLocationAlias(
            location = "서울특별시 강남구 테헤란로 212",
            goPrev = {},
            onComplete = {}
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_funnel_check_entered_hub_info) {
        // TODO: manage state...
        FunnelCheckEnteredHubInfo(
            location = "서울특별시 강남구 테헤란로 212",
            locationAlias = "서울특별시 집",
            goPrev = {},
            goStartScreen = {},
            onConfirm = {}
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_redirect_main_after_register_hub_and_location) {
        // TODO: manage state...
        RedirectMainAfterRegisterHubAndLocation(
            locationAlias = "서울특별시 집",
            location = "서울특별시 강남구 테헤란로 212",
            goNext = {}
        )
        return
    }

    if (firstSettingFunnelsUiState.currentStepId == R.string.first_setting_funnel_enter_location_to_register_hub) {
        // TODO: manage state...
        FunnelEnterLocationToRegisterHub(
            goPrev = {
                firstSettingFunnelsViewModel.updateCurrentStep(stepId = R.string.first_setting_funnel_auto_register_location)
            },
            onSelectLocation = {
                // TODO
            }
        )
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