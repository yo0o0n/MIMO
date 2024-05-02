package com.mimo.android


import androidx.compose.material3.*
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mimo.android.components.BackgroundImage

import com.mimo.android.health.HealthConnectManager
import com.mimo.android.screens.Navigation
import com.mimo.android.screens.Router
import com.mimo.android.screens.firstsetting.FirstSettingRootScreen
import com.mimo.android.screens.login.LoginScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MimoApp(
    healthConnectManager: HealthConnectManager,
    context: Context,
    serviceRunning: Boolean,
    currentLocation: String?,
    onClickForeground: () -> Unit,
    checkCameraPermission: (() -> Unit)? = null,
    ){
    MaterialTheme {
        BackgroundImage {
            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val availability by healthConnectManager.availability

            var isLoggedIn by remember { mutableStateOf(false) }
            var isFinishedFirstSetting by remember { mutableStateOf(false) }

            // TODO: kakao Login
            fun handleKakaoLogin(){
                scope.launch {
                    delay(2000)
                    isLoggedIn = true
                }
            }

            fun handleFinishFirstSetting(){
                scope.launch {
                    delay(2000)
                    isFinishedFirstSetting = true
                }
            }

            Router(
                navController = navController,
                healthConnectManager = healthConnectManager,
                serviceRunning = serviceRunning,
                currentLocation = currentLocation,
                onClickForeground = onClickForeground,
            )

            if (!isLoggedIn) {
                LoginScreen(
                    onLoginWithKakao = ::handleKakaoLogin
                )
                return@BackgroundImage
            }

            if (!isFinishedFirstSetting) {
                FirstSettingRootScreen(
                    onFinishFirstSetting = ::handleFinishFirstSetting,
                    checkCameraPermission = checkCameraPermission
                )
                return@BackgroundImage
            }

            Navigation(
                navController = navController
            )
        }
    }
}