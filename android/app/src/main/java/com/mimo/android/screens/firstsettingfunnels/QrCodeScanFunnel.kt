package com.mimo.android.screens.firstsettingfunnels

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import com.mimo.android.components.*
import com.mimo.android.components.base.*

@Composable
fun QrCodeScanFunnel(
    goPrev: (() -> Unit)? = null,
    checkCameraPermission: (() -> Unit)? = null,
){
    fun handleScanQRCode(){
        if (checkCameraPermission != null) {
            checkCameraPermission()
        }
    }

    fun handleGoPrev(){
        goPrev?.invoke()
    }

    BackHandler {
        goPrev?.invoke()
    }

    Column {
        Icon(imageVector = Icons.Filled.ArrowBack, onClick = ::handleGoPrev)
        Spacer(modifier = Modifier.padding(14.dp))

        HeadingLarge(text = "허브를 등록할게요", fontSize = Size.lg)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(text = "QR코드 스캔", onClick = ::handleScanQRCode)
        }
    }
}