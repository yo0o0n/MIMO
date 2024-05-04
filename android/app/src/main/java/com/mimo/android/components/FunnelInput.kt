package com.mimo.android.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mimo.android.components.base.Size
import com.mimo.android.components.base.TextSizeMatcher
import com.mimo.android.ui.theme.Gray400
import com.mimo.android.ui.theme.Teal400
import com.mimo.android.ui.theme.Teal500

@Preview
@Composable
fun FunnelInput(
    text: String = "",
    onChange: ((newText: String) -> Unit)? = null,
    onClear: (() -> Unit)? = null,
    placeholder: String = "입력해주세요",
    description: String? = "as"
){
    fun handleChange(newText: String){
        onChange?.invoke(newText)
    }

    fun handleClear(){
        onClear?.invoke()
    }

    Column {
        if (text.isNotEmpty() && description != null) {
            Text(text = description, color = Teal500, fontSize = Size.xs)
        } else {
            Spacer(modifier = Modifier.padding(6.dp))
        }

        BasicTextField(
            value = text,
            onValueChange = ::handleChange,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = TextSizeMatcher.getOrElse(Size.xl3) { 28.sp },
            ),
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Row{
                        Text(
                            text = placeholder,
                            color = Gray400,
                            fontSize = Size.xl3,
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        innerTextField()
                        Icon(
                            imageVector = Icons.Default.Clear, color = Teal400,
                            onClick = ::handleClear
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.padding(1.dp))
        HorizontalDivider(opacity = 1.0f)
    }
}