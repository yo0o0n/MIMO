package com.mimo.android.screens.sleep

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mimo.android.components.HeadingLarge
import com.mimo.android.components.base.Size

@Composable
fun SleepScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.padding(24.dp))

        HeadingLarge(text = "수면,,,zzZ", fontSize = Size.lg)
    }
}