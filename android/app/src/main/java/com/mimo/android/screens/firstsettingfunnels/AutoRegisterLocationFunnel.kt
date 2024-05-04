package com.mimo.android.screens.firstsettingfunnels

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mimo.android.components.*
import com.mimo.android.components.base.*
import com.mimo.android.ui.theme.*

@Composable
fun AutoRegisterLocationFunnel(
    location: String,
    onDirectlyEnterLocation: (() -> Unit)? = null
){
    Column {
        Spacer(modifier = Modifier.padding(30.dp))

        HeadingLarge(text = "현재 위치", fontSize = Size.lg)
        Spacer(modifier = Modifier.padding(8.dp))
        HeadingSmall(text = location, color = Teal100)
        
        Spacer(modifier = Modifier.padding(16.dp))
        HeadingLarge(text = "이 장소에 허브를 등록할까요?", fontSize = Size.lg)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Button(
                        text = "장소 직접 입력", color = Gray600, hasBorder = false,
                        onClick = onDirectlyEnterLocation
                    )
                }
                item {
                    Button(text = "네 등록할게요")
                }
            }
        }
    }
}