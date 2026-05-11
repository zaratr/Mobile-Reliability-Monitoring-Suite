plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.firebase.firebase-perf")
    id("io.sentry.android.gradle") version "3.12.0"
}

android {
    namespace = "com.reliability.suite"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.reliability.suite"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

sentry {
    includeProGuardMapping = true
    autoUploadProGuardMapping = true
    uploadNativeSymbols = false
    tracingInstrumentation {
        enabled = true
    }
}

dependencies {
    implementation("io.sentry:sentry-android:6.31.0")
    implementation("com.google.firebase:firebase-perf-ktx:20.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
