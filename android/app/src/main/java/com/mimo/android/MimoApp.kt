package com.mimo.android

import androidx.compose.material3.*
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mimo.android.components.BackgroundImage
import com.mimo.android.services.health.HealthConnectManager
import com.mimo.android.screens.Navigation
import com.mimo.android.screens.Router
import com.mimo.android.screens.firstsettingfunnels.*
import com.mimo.android.screens.login.LoginScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MimoApp(
    authViewModel: AuthViewModel,
    qrCodeViewModel: QrCodeViewModel,
    firstSettingFunnelsViewModel: FirstSettingFunnelsViewModel,
    healthConnectManager: HealthConnectManager,
    context: Context,
    serviceRunning: Boolean,
    currentLocation: String?,
    onClickForeground: () -> Unit,
    checkCameraPermission: () -> Unit
    ){
    MaterialTheme {
        BackgroundImage {
            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val availability by healthConnectManager.availability

            val authUiState by authViewModel.uiState.collectAsState()
            val firstSettingFunnelsUiState by firstSettingFunnelsViewModel.uiState.collectAsState()

            // TODO: 실제 kakao-login 구현
            fun handleKakaoLoginMock(){
                val user = User(
                    username = "용상윤",
                    accessToken = "123",
                    refreshToken = "456"
                )
                authViewModel.login(
                    user = user
                )
            }

            Router(
                navController = navController,
                healthConnectManager = healthConnectManager,
                serviceRunning = serviceRunning,
                currentLocation = currentLocation,
                onClickForeground = onClickForeground,
            )

            if (authUiState.user == null) {
                LoginScreen(
                    onLoginWithKakao = ::handleKakaoLoginMock
                )
                return@BackgroundImage
            }

            if (firstSettingFunnelsUiState.currentStepId != null) {
                FirstSettingFunnelsRoot(
                    qrCodeViewModel = qrCodeViewModel,
                    firstSettingFunnelsViewModel = firstSettingFunnelsViewModel,
                    checkCameraPermission = checkCameraPermission,
                    context = context
                )
                return@BackgroundImage
            }

            Navigation(
                navController = navController
            )
        }
    }
}