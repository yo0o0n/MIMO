package com.mimo.android.screens.firstsetting

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
fun ReadQRCodeScreen(
    goPrev: (() -> Unit)? = null,
    goNext: (() -> Unit)? = null
){

    fun handleGoPrev(){
        goPrev?.invoke()
    }
    
    Column {
        Icon(imageVector = Icons.Filled.ArrowBack, onClick = ::handleGoPrev)
        Spacer(modifier = Modifier.padding(14.dp))

        HeadingLarge(text = "허브를 등록할게요", fontSize = Size.lg)
    }
}