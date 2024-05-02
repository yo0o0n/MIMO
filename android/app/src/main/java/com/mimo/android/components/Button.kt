package com.mimo.android.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mimo.android.ui.theme.Teal600
import com.mimo.android.ui.theme.Teal700

@Composable fun Button(
    onClick: (() -> Unit)? = null,
    text: String,
    width: Dp? = null,
){

    val modifierWidth = if (width != null) Modifier.width(width) else Modifier.fillMaxWidth()

    fun handleClick(){
        if (onClick != null) {
            onClick()
        }
    }

    Button(
        onClick = ::handleClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Teal600),
        modifier = modifierWidth.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Teal700)
    ) {
        HeadingSmall(text = text)
    }
}