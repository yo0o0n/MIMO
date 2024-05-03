package com.mimo.android.screens.firstsetting

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kakao.sdk.user.UserApiClient
import com.mimo.android.components.Button
import com.mimo.android.components.HeadingLarge
import com.mimo.android.components.Icon
import com.mimo.android.components.base.Size

@Preview
@Composable
fun ReadQRCodeScreen(
    goPrev: (() -> Unit)? = null,
    goNext: (() -> Unit)? = null,
    checkCameraPermission: (() -> Unit)? = null,
){

    fun handleScanQRCode(){
        checkCameraPermission?.invoke()
    }

    fun handleGoPrev(){
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
            Button(text = "로그아웃", onClick = {
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Log.e("MIMO KAKAO", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    }
                    else {
                        Log.i("MIMO KAKAO", "로그아웃 성공. SDK에서 토큰 삭제됨")
                    }
                }
            })
            Button(text = "QR코드 스캔하기", onClick = ::handleScanQRCode)
        }
    }
}