package com.mimo.android.screens.main.myhome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mimo.android.components.ButtonSmall
import com.mimo.android.components.CardType
import com.mimo.android.components.HeadingLarge
import com.mimo.android.components.HeadingSmall
import com.mimo.android.components.Text
import com.mimo.android.components.TransparentCard
import com.mimo.android.components.base.Size
import com.mimo.android.ui.theme.Teal100

@Composable
fun MyHomeScreen(
    navController: NavHostController,
    currentHome: HubHome?,
    anotherHomes: Array<HubHome>?
){
    Column(
        modifier = Modifier
            .size(700.dp)
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

        if (currentHome == null) {
            Box(){
               Text(text = "등록된 거주지가 없어요")
            }
        }

        if (currentHome != null) {
            Card(
                items = currentHome.items,
                homeName = currentHome.homeName,
                address = currentHome.address
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        HeadingSmall(text = "다른 거주지")
        Spacer(modifier = Modifier.padding(8.dp))

        anotherHomes?.forEachIndexed { index, anotherHome ->
            Card(
                items = anotherHome.items,
                homeName = anotherHome.homeName,
                address = anotherHome.address
            )

            if (index < anotherHomes.size) {
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Card(
    items: Array<String>?,
    homeName: String,
    address: String
){
    Box(
        modifier = Modifier.combinedClickable(
            onClick = { println(homeName) },
            onLongClick = { println("${homeName} 꾹 눌렀음!!") }
        )
    ){
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
                        if (items != null) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                items.forEach { item -> CardType(text = item) }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    HeadingSmall(text = homeName)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    HeadingSmall(text = address, fontSize = Size.xs, color = Teal100)
                }
            }
        )
    }
}

data class HubHome(
    val items: Array<String>? = null,
    val homeName: String,
    val address: String
)

@Preview
@Composable
private fun MyHomeScreenPreview(){
    val navController = NavHostController(LocalContext.current)
    val currentHome = HubHome(
        items = arrayOf("조명", "무드등"),
        homeName = "상윤이의 자취방",
        address = "서울특별시 관악구 봉천동 1234-56"
    )
    val anotherHomes: Array<HubHome> = arrayOf(
        HubHome(
            items = arrayOf("조명", "창문", "커튼"),
            homeName = "상윤이의 본가",
            address = "경기도 고양시 일산서구 산현로12"
        ),
        HubHome(
            items = arrayOf("조명", "커튼"),
            homeName = "싸피",
            address = "서울특별시 강남구 테헤란로 212"
        )
    )

    MyHomeScreen(
        navController = navController,
        currentHome = currentHome,
        anotherHomes = anotherHomes
    )
}