package com.mimo.android


import androidx.compose.material3.*
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
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
    qrData: String? = null
    ){
    MaterialTheme {
        BackgroundImage {
            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val availability by healthConnectManager.availability

            var isLoading by remember { mutableStateOf(true) }
            var isLoggedIn by remember { mutableStateOf(AuthApiClient.instance.hasToken()) }
            var isFinishedFirstSetting by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = Unit) {
                try {
                    val hasToken = AuthApiClient.instance.hasToken()
                    println(hasToken)
                } catch(e: Exception){
                    e.printStackTrace()
                } finally {
                    isLoading = false
                }
            }

            // TODO: kakao Login
            fun handleKakaoLogin(){
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    if (error != null) {
                        Log.e("MIMO Kakao", "로그인 실패", error)
                    }
                    else if (token != null) {
                        Log.i("MIMO Kakao", "로그인 성공 ${token.accessToken}")
                        println("accessToken : ${token.accessToken}")
                        println("refreshToken : ${token.refreshToken}")
                        isLoggedIn = true
                    }
                }
            }

            fun handleKakaoLoginMock(){
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

            if (isLoading) {
                return@BackgroundImage
            }

            if (!isLoggedIn) {
                LoginScreen(
                    onLoginWithKakao = ::handleKakaoLogin
                )
                return@BackgroundImage
            }

            if (!isFinishedFirstSetting) {
                FirstSettingRootScreen(
                    onFinishFirstSetting = ::handleFinishFirstSetting,
                    checkCameraPermission = checkCameraPermission,
                    qrData = qrData
                )
                return@BackgroundImage
            }

            Navigation(
                navController = navController
            )
        }
    }
}