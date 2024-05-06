package com.mimo.android.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mimo.android.components.BackgroundImage
import com.mimo.android.components.HeadingLarge
import com.mimo.android.components.base.Size
import com.mimo.android.screens.Navigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Layout(
    navController: NavHostController,
    children: @Composable () -> Unit
){

    Scaffold(
        bottomBar = {
            Navigation(navController = navController)
        },
        content = {
            Column (
                modifier = Modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier.padding(16.dp)){
                    children()
                }
            }
        }
    )
}

@Preview
@Composable
fun LayoutPreview(){
    Layout(
        navController = NavHostController(LocalContext.current),
        children = {
            HeadingLarge(text = "우리 집,,", fontSize = Size.lg)
        }
    )
}