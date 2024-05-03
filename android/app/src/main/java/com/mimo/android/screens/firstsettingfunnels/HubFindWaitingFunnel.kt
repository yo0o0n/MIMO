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

@Preview
@Composable
fun HubFindWaitingFunnel(
    goPrev: (() -> Unit)? = null
){
    fun handleGoPrev(){
        goPrev?.invoke()
    }

    BackHandler {
        return@BackHandler
    }

    Column {
        Icon(imageVector = Icons.Filled.ArrowBack, onClick = ::handleGoPrev)
        Spacer(modifier = Modifier.padding(14.dp))

        HeadingLarge(text = "허브를 찾고 있어요", fontSize = Size.lg)
        HeadingLarge(text = "잠시만 기다려주세요", fontSize = Size.lg)
    }
}