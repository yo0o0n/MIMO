package com.mimo.android.screens.main.myhome

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mimo.android.components.*
import com.mimo.android.components.base.Size

@Composable
fun MyHomeDetailScreen(
    navController: NavHostController,
    home: Home
){
    fun handleGoPrev(){
        navController.navigateUp()
    }

    BackHandler {
        handleGoPrev()
    }

    ScrollView {
        Icon(imageVector = Icons.Filled.ArrowBack, onClick = ::handleGoPrev)
        Spacer(modifier = Modifier.padding(14.dp))

        HeadingLarge(text = home.homeName, fontSize = Size.lg)
    }
}

@Preview
@Composable
private fun MyHomeDetailScreenPreview(){
    val navController = NavHostController(LocalContext.current)
    val home = Home(
        homeId = "1",
        homeName = "상윤이의 자취방",
        address = "서울특별시 관악구 봉천동 1234-56",
        items = arrayOf("조명", "무드등")
    )

    MyHomeDetailScreen(
        navController = navController,
        home = home
    )
}