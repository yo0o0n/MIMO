package com.mimo.android.screens.firstsettingfunnels

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mimo.android.components.HeadingLarge
import com.mimo.android.components.HeadingSmall
import com.mimo.android.components.Icon
import com.mimo.android.components.base.Size
import com.mimo.android.ui.theme.Teal100

@Composable
fun MakeLocationAliasFunnel(
    location: String,
    goPrev: (() -> Unit)? = null,
    onComplete: ((aliasText: String) -> Unit)? = null
){
    fun handleComplete(){
        // TODO: inputText가 없으면 return
        onComplete?.invoke("TODO...")
    }

    BackHandler {
        goPrev?.invoke()
    }

    Column {
        Icon(imageVector = Icons.Filled.ArrowBack, onClick = goPrev)
        Spacer(modifier = Modifier.padding(14.dp))

        HeadingSmall(text = location, color = Teal100)
        Spacer(modifier = Modifier.padding(16.dp))
        HeadingLarge(text = "이 장소의 별칭을 지어주세요", fontSize = Size.lg)
        Spacer(modifier = Modifier.padding(16.dp))

        // TODO: Input
    }
}