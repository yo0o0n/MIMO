import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}

// Load local.properties file
val localPropertiesFile = rootDir.resolve("local.properties")
val localProperties = Properties()

if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val apiKey: String = localProperties.getProperty("API_KEY", "")
val kakaoSdkAppKey: String = localProperties.getProperty("kakao_sdk_appkey", "")

allprojects {
    extra["API_KEY"] = apiKey
    extra["kakao_sdk_appkey"] = kakaoSdkAppKey
}