package com.mimo.android.screens.firstsettingfunnels

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimo.android.components.HeadingLarge
import com.mimo.android.components.Icon
import com.mimo.android.components.base.Size

@Composable
fun CheckEnteredHubInfoFunnel(
    locationAlias: String,
    location: String,
    goPrev: () -> Unit,
    goStartScreen: () -> Unit,
    onConfirm: () -> Unit
){

    BackHandler {
        goPrev.invoke()
        return@BackHandler
    }

    Column {
        Icon(imageVector = Icons.Filled.ArrowBack, onClick = goPrev)
        Spacer(modifier = Modifier.padding(14.dp))

        HeadingLarge(text = "아래 정보로 허브를 등록할까요?", fontSize = Size.lg)
    }
}

@Preview
@Composable
fun CheckEnteredHubInfoFunnelPreview() {
    CheckEnteredHubInfoFunnel(
        location = "서울특별시 강남구 테헤란로 212",
        locationAlias = "서울특별시 집",
        goPrev = {},
        goStartScreen = {},
        onConfirm = {}
    )
}