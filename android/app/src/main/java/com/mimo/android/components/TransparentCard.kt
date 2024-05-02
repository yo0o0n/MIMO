package com.mimo.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.mimo.android.ui.theme.Teal400
import com.mimo.android.ui.theme.Teal900

@Composable
fun TransparentCard(
    children: @Composable () -> Unit
){

    val borderShape = RoundedCornerShape(16.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Teal900,
                shape = borderShape
            )
            .background(
                color = Teal400.copy(alpha = 0.2f),
                shape = borderShape
            ),
        contentAlignment = Alignment.Center,
    ) {
        children()
    }
}