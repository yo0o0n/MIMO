package com.mimo.android.screens.myprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mimo.android.health.HealthConnectManager
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyProfileScreen(healthConnectManager: HealthConnectManager){

    var stepCount by remember {
        mutableStateOf<Long>(0)
    }

    LaunchedEffect(Unit) {
        val startOfDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS)
        val now = Instant.now()
        stepCount = healthConnectManager.readSteps(startOfDay.toInstant(), now)
    }
    
    Column {
        Text(text = "MyProfile Screen", style = TextStyle(
            fontSize = 32.sp
        ))
        
        Spacer(modifier = Modifier.padding(32.dp))

        Text(text = "걸음 수 : ${stepCount}")
    }
}