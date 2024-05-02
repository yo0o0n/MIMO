package com.mimo.android.screens.firstsetting

import androidx.compose.runtime.Composable
import com.mimo.android.components.Button
import com.mimo.android.components.Text

@Composable
fun ReadQRCodeScreen(
    goPrev: () -> Unit,
    goNext: () -> Unit
){
    Button(text = "뒤로가기", onClick = goPrev)
    Text(text = "QR 읽어줘잉")
}