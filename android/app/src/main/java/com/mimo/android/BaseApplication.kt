package com.mimo.android

import android.app.Application
import com.mimo.android.health.HealthConnectManager

class BaseApplication: Application(){
    val healthConnectManager by lazy {
        HealthConnectManager(this)
    }
}