package com.mimo.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mimo.android.ui.theme.Teal400

@Composable
fun HorizontalDivider(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                color = Teal400.copy(alpha = 0.2f)
            ),
    )
}