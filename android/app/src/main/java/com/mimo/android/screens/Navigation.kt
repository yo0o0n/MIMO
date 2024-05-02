package com.mimo.android.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mimo.android.R
import com.mimo.android.health.HealthConnectManager
import com.mimo.android.screens.firstsetting.FirstSettingStartScreen
import com.mimo.android.screens.firstsetting.ReadQRCodeScreen
import com.mimo.android.screens.myhome.MyHomeScreen
import com.mimo.android.screens.myprofile.MyProfileScreen
import com.mimo.android.screens.sleep.SleepScreen

/**
 * Provides the navigation in the app.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Navigation(
    navController: NavHostController
) {

    val scope = rememberCoroutineScope()

    Scaffold (
        bottomBar = {
            BottomAppBar(
                content = {
                    HorizontalButtonNavigation(navController)
                }
            )
        }
    ) {

    }
}

@Composable
fun HorizontalButtonNavigation(navController: NavController) {
    Button(
        contentPadding = PaddingValues(0.dp),
        shape = MaterialTheme.shapes.small,
        onClick = {
            navController.navigate(Screen.MyHomeScreen.route)
        }
    ) {
        Text(text = "우리집")
    }

    Button(
        contentPadding = PaddingValues(0.dp),
        shape = MaterialTheme.shapes.small,
        onClick = {
            navController.navigate(Screen.SleepScreen.route)
        }
    ) {
        Text(text = "수면")
    }

    Button(
        contentPadding = PaddingValues(0.dp),
        shape = MaterialTheme.shapes.small,
        onClick = {
            navController.navigate(Screen.MyProfileScreen.route)
        }
    ) {
        Text(text = "내정보")
    }

    Button(
        contentPadding = PaddingValues(0.dp),
        shape = MaterialTheme.shapes.small,
        onClick = {
            navController.navigate(Screen.ForegroundServiceSampleScreen.route)
        }
    ) {
        Text(text = "테스트1")
    }
}