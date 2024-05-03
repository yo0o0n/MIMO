package com.mimo.android.screens.myhome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mimo.android.R
import com.mimo.android.components.Button
import com.mimo.android.components.HeadingLarge
import com.mimo.android.components.HeadingSmall
import com.mimo.android.components.HorizontalDivider
import com.mimo.android.components.Icon
import com.mimo.android.components.Logo
import com.mimo.android.components.TransparentCard
import com.mimo.android.components.base.GifImage
import com.mimo.android.components.base.Size
import com.mimo.android.ui.theme.Teal100
import com.mimo.android.ui.theme.Teal50

@Composable
fun MyHomeScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.padding(24.dp))

        HeadingLarge(text = "우리 집,,", fontSize = Size.lg)
    }
}