package com.mimo.android.screens.login

import androidx.compose.runtime.Composable
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import com.mimo.android.components.*
import androidx.compose.ui.unit.dp

import com.mimo.android.components.base.Size

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    onLoginWithKakao: (() -> Unit)? = null,
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeadingLarge(text = "우리 집 미라클모닝", fontSize = Size.lg)
        HeadingLarge(text = "MIMO", fontSize = Size.lg)

        Spacer(modifier = Modifier.padding(10.dp))

        Button(text = "카카오로 로그인", onClick = onLoginWithKakao)
    }
}