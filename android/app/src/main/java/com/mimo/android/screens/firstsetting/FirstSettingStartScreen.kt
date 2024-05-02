package com.mimo.android.screens.firstsetting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mimo.android.R
import com.mimo.android.components.Button
import com.mimo.android.components.HeadingLarge
import com.mimo.android.components.HeadingSmall
import com.mimo.android.components.Logo
import com.mimo.android.components.Text
import com.mimo.android.components.TransparentCard
import com.mimo.android.components.HorizontalDivider
import com.mimo.android.components.base.GifImage
import com.mimo.android.components.base.Size
import com.mimo.android.ui.theme.Teal100
import com.mimo.android.ui.theme.Teal50

@Composable
fun FirstSettingStartScreen(
    goNext: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Logo()
        Spacer(modifier = Modifier.padding(4.dp))
        HeadingLarge(text = "MIMO", fontSize = Size.lg)
        HeadingLarge(text = "허브 등록을 시작할게요", fontSize = Size.lg)
        Spacer(modifier = Modifier.padding(12.dp))
        TransparentCard {
            Row(
                modifier = Modifier.padding(vertical = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GifImage(R.drawable.moon_animation_image)
                Column {
                    HeadingSmall(text = "이제부터 MIMO가", fontSize = Size.lg, color = Teal50)
                    Text(text = "당신의 수면 활동을 도와드릴게요")
                }
            }
        }
        Spacer(modifier = Modifier.padding(12.dp))
        HorizontalDivider()

        Spacer(modifier = Modifier.padding(16.dp))
        HeadingSmall(text = "MIMO와 함께하면", fontSize = Size.lg)
        Spacer(modifier = Modifier.padding(16.dp))

        Column {
            Row {
                Column {
                    Text(text = "수면의 질을 높여")
                    HeadingSmall(text = "편안하게 잠을 잘 수 있어요", fontSize = Size.md, color = Teal100)
                }
                // Image
            }

            Spacer(modifier = Modifier.padding(12.dp))

            Row {
                Column {
                    Text(text = "수면 사이클에 맞춰")
                    HeadingSmall(text = "상쾌하게 기상할 수 있어요", fontSize = Size.md, color = Teal100)
                }
                // Image
            }

            Spacer(modifier = Modifier.padding(12.dp))

            Row {
                Column {
                    Text(text = "생체 데이터를 통해")
                    HeadingSmall(text = "최적의 수면 환경을 만들 수 있어요", fontSize = Size.md, color = Teal100)
                }
                // Image
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(text = "시작하기", onClick = goNext)
        }
    }
}