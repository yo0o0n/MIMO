package com.mimo.android.components.base

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun DetectOSBackButton(
    event: (() -> Unit)? = null,
    children: @Composable () -> Unit
) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var enabled by rememberSaveable { mutableStateOf(true) }

    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                event?.invoke()
            }
        }
    }

    DisposableEffect(Unit) {
        backCallback.isEnabled = enabled
        onBackPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }

    children()
}