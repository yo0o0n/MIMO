package com.mimo.android.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * Provides the navigation in the app.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Navigation(
    navController: NavHostController
) {

    val scope = rememberCoroutineScope()

    Row {
        Button(
            contentPadding = PaddingValues(0.dp),
            shape = MaterialTheme.shapes.small,
            onClick = {
                navController.navigate(Screen.MyHomeScreen.route) {
                    popUpTo(0)
                }
            }
        ) {
            Text(text = "우리집")
        }

        Button(
            contentPadding = PaddingValues(0.dp),
            shape = MaterialTheme.shapes.small,
            onClick = {
                navController.navigate(Screen.SleepScreen.route) {
                    popUpTo(0)
                }
            }
        ) {
            Text(text = "수면")
        }

        Button(
            contentPadding = PaddingValues(0.dp),
            shape = MaterialTheme.shapes.small,
            onClick = {
                navController.navigate(Screen.MyProfileScreen.route) {
                    popUpTo(0)
                }
            }
        ) {
            Text(text = "내정보")
        }
    }
}