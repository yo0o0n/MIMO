package com.mimo.android.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mimo.android.R
import com.mimo.android.health.HealthConnectManager
import com.mimo.android.screens.myhome.MyHomeScreen
import com.mimo.android.screens.myprofile.MyProfileScreen
import com.mimo.android.screens.sleep.SleepScreen

@Composable
fun Router(
    navController: NavHostController,
    healthConnectManager: HealthConnectManager,
    serviceRunning: Boolean,
    currentLocation: String?,
    onClickForeground: () -> Unit,
){
    NavHost(navController = navController, startDestination = Screen.EmptyScreen.route) {
        //val availability by healthConnectManager.availability

        // default
        composable(Screen.EmptyScreen.route) {
            EmptyScreen()
        }

        // main
        composable(Screen.MyHomeScreen.route) {
            MyHomeScreen()
        }
        composable(Screen.SleepScreen.route) {
            SleepScreen()
        }
        composable(Screen.MyProfileScreen.route) {
            MyProfileScreen(healthConnectManager)
        }
        composable(Screen.ForegroundServiceSampleScreen.route) {
            ForegroundServiceSampleScreen(
                serviceRunning = serviceRunning,
                currentLocation = currentLocation,
                onClick = onClickForeground)
        }
    }
}

enum class Screen(val route: String, val titleId: Int) {
    EmptyScreen("empty_screen", R.string.empty_screen),
    MyHomeScreen("my_home_screen", R.string.my_home_screen),
    SleepScreen("sleep_screen", R.string.sleep_screen),
    MyProfileScreen("my_profile_screen", R.string.my_profile_screen),
    ForegroundServiceSampleScreen("foreground_service_sample_screen", R.string.foreground_service_sample_screen)
}