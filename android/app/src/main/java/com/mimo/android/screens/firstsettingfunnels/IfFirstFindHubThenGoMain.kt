package com.mimo.android.screens.firstsettingfunnels

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimo.android.components.HeadingLarge
import com.mimo.android.components.HeadingSmall
import com.mimo.android.components.base.Size
import com.mimo.android.ui.theme.Teal100
import kotlinx.coroutines.delay

@Composable
fun IfFirstFindHubThenGoMain(
    homeName: String,
    homeLocation: String,
    goNext: () -> Unit
){

    LaunchedEffect(Unit) {
        delay(1000)
        goNext()
    }

    BackHandler {
        return@BackHandler
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.padding(30.dp))

        HeadingLarge(text = homeName, fontSize = Size.lg, color = Teal100)
        Spacer(modifier = Modifier.padding(8.dp))
        HeadingSmall(text = homeLocation, color = Teal100)

        Spacer(modifier = Modifier.padding(16.dp))

        HeadingLarge(text = "허브에 등록되었어요!", fontSize = Size.lg)
        Spacer(modifier = Modifier.padding(4.dp))
        HeadingLarge(text = "메인 화면으로 이동할게요", fontSize = Size.lg)
    }
}

@Preview
@Composable
fun IfFirstFindHubThenGoMainPreview(){
    IfFirstFindHubThenGoMain(
        homeName = "상윤이의 본가",
        homeLocation = "경기도 고양시 일산서구 산현로 34",
        goNext = {}
    )
}