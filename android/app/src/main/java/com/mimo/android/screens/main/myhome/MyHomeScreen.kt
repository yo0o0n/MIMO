package com.mimo.android.screens.main.myhome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mimo.android.components.Button
import com.mimo.android.components.ButtonSmall
import com.mimo.android.components.CardType
import com.mimo.android.components.HeadingLarge
import com.mimo.android.components.HeadingSmall
import com.mimo.android.components.TransparentCard
import com.mimo.android.components.base.Size
import com.mimo.android.screens.main.Layout
import com.mimo.android.screens.main.getScreenHeight
import com.mimo.android.ui.theme.Teal100

@Composable
fun MyHomeScreen(
    navController: NavHostController
){
    Column(
        modifier = Modifier
            .size(750.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            HeadingLarge(text = "우리 집", fontSize = Size.lg)
            ButtonSmall(text = "거주지 추가")
        }
        Spacer(modifier = Modifier.padding(14.dp))

        HeadingSmall(text = "현재 거주지")
        Spacer(modifier = Modifier.padding(8.dp))
        TransparentCard(
            borderRadius = 8.dp,
            children = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(120.dp)) {
                    Row (
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        CardType(text = "조명")
                        CardType(text = "무드등")
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    HeadingSmall(text = "상윤이의 자취방")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    HeadingSmall(text = "서울특별시 관악구 봉천동 1234-56", fontSize = Size.xs, color = Teal100)
                }
            }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        HeadingSmall(text = "다른 거주지")
        Spacer(modifier = Modifier.padding(8.dp))
        TransparentCard(
            borderRadius = 8.dp,
            children = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(120.dp)) {
                    Row (
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        CardType(text = "조명")
                        CardType(text = "무드등")
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    HeadingSmall(text = "상윤이의 자취방")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    HeadingSmall(text = "서울특별시 관악구 봉천동 1234-56", fontSize = Size.xs, color = Teal100)
                }
            }
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TransparentCard(
            borderRadius = 8.dp,
            children = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(120.dp)) {
                    Row (
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        CardType(text = "조명")
                        CardType(text = "무드등")
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    HeadingSmall(text = "상윤이의 자취방")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    HeadingSmall(text = "서울특별시 관악구 봉천동 1234-56", fontSize = Size.xs, color = Teal100)
                }
            }
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TransparentCard(
            borderRadius = 8.dp,
            children = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(120.dp)) {
                    Row (
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        CardType(text = "조명")
                        CardType(text = "무드등")
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    HeadingSmall(text = "상윤이의 자취방")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    HeadingSmall(text = "서울특별시 관악구 봉천동 1234-56", fontSize = Size.xs, color = Teal100)
                }
            }
        )
    }

//    Column (
//        modifier = Modifier.fillMaxSize()
//    ) {
//
//
//        Spacer(modifier = Modifier.weight(1f))
//        Navigation(navController = navController)
//    }
}

@Preview
@Composable
private fun MyHomeScreenPreview(){
    val navController = NavHostController(LocalContext.current)

    Layout(
        navController = navController,
        children = {
            MyHomeScreen(
                navController = navController
            )
        }
    )
}

