package com.mimo.android.screens.firstsetting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun FirstSettingRootScreen(
    onFinishFirstSetting: () -> Unit
){
    var step by remember { mutableStateOf("0") }

    if (step == "0") {
        FirstSettingStartScreen(
            goNext = { step = "1" }
        )
        return
    }

    if (step == "1") {
        ReadQRCodeScreen(
            goPrev = { step = "0" },
            goNext = { step = "2" }
        )
        return
    }
}