package com.mimo.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import com.mimo.android.R
import com.mimo.android.screens.main.myhome.MyHomeScreen
import com.mimo.android.services.health.HealthConnectManager
import com.mimo.android.screens.main.myprofile.MyProfileScreen
import com.mimo.android.screens.main.sleep.SleepScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Router(
    navController: NavHostController,
    healthConnectManager: HealthConnectManager,
//    serviceRunning: Boolean? = null,
//    currentLocation: String? = null,
//    onClickForeground: (() -> Unit)? = null,
){
    NavHost(navController = navController, startDestination = Screen.MyHomeScreen.route) {
        //val availability by healthConnectManager.availability

        // main
        composable(Screen.MyHomeScreen.route) {
            Column (
                modifier = Modifier.fillMaxSize()
            ) {
                MyHomeScreen()
                Spacer(modifier = Modifier.weight(1f))
                Navigation(navController = navController)
            }
            return@composable
        }
        composable(Screen.SleepScreen.route) {
            Column {
                SleepScreen()
                Spacer(modifier = Modifier.weight(1f))
                Navigation(navController = navController)
            }
            return@composable
        }
        composable(Screen.MyProfileScreen.route) {
            Column {
                MyProfileScreen(healthConnectManager)
                Spacer(modifier = Modifier.weight(1f))
                Navigation(navController = navController)
            }
            return@composable
        }


//        composable(Screen.ForegroundServiceSampleScreen.route) {
//            ForegroundServiceSampleScreen(
//                serviceRunning = serviceRunning,
//                currentLocation = currentLocation,
//                onClick = onClickForeground)
//        }
    }
}

enum class Screen(val route: String, val titleId: Int) {
    MyHomeScreen("my_home_screen", R.string.my_home_screen),
    SleepScreen("sleep_screen", R.string.sleep_screen),
    MyProfileScreen("my_profile_screen", R.string.my_profile_screen),
    //ForegroundServiceSampleScreen("foreground_service_sample_screen", R.string.foreground_service_sample_screen)
}