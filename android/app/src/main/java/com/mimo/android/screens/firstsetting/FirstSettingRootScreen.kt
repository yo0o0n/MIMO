package com.mimo.android.screens.firstsetting

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import com.mimo.android.components.base.DetectOSBackButton

@Composable
fun FirstSettingRootScreen(
    onFinishFirstSetting: (() -> Unit)? = null,
    checkCameraPermission: (() -> Unit)? = null,
    qrData: String? = null
){
    var step by remember { mutableStateOf("0") }

    if (step == "0") {
        FirstSettingStartScreen(
            goNext = {
                step = "1"
            }
        )
        return
    }

    if (step == "1") {
        DetectOSBackButton(
            event = { step = "0" }
        ) {
            ReadQRCodeScreen(
                goPrev = { step = "0" },
                goNext = { step = "2" },
                checkCameraPermission = checkCameraPermission
            )

            checkCameraPermission?.invoke()
            println("QR data : ${qrData}")
        }
        return
    }
}