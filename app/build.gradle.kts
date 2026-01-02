import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")   // WAJIB untuk Room
}

android {
    namespace = "com.example.plantmate"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.plantmate"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Navigation Compose (VERSI LAMA TETAP ADA)
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("androidx.navigation:navigation-compose:2.7.2")

    // Coil image loader
    implementation("io.coil-kt:coil-compose:2.6.0")

    // CameraX
    implementation("androidx.camera:camera-camera2:1.3.3")
    implementation("androidx.camera:camera-lifecycle:1.3.3")
    implementation("androidx.camera:camera-view:1.3.3")
    implementation("androidx.camera:camera-core:1.3.3")
    implementation("androidx.camera:camera-extensions:1.3.3")

    // JSoup
    implementation("org.jsoup:jsoup:1.17.2")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // ViewModel Compose (LAMA)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // ✅ INI YANG KURANG (DITAMBAHKAN)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    // ROOM Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation(libs.androidx.appcompat)
    kapt("androidx.room:room-compiler:2.6.1")

    // Compose Animation & Accompanist (LAMA, TETAP ADA)
    implementation("androidx.compose.animation:animation:1.6.1")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")

    // Accompanist core (WAJIB untuk animation nav)
    implementation("com.google.accompanist:accompanist-navigation-material:0.34.0")

// Compose animation runtime (dibutuhkan accompanist lama)
    implementation("androidx.compose.animation:animation")

// Compose runtime (accompanist tidak selalu resolve dari BOM)
    implementation("androidx.compose.runtime:runtime")

    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

    // ML Kit Image Labeling
    implementation("com.google.mlkit:image-labeling:17.0.9")

    // ML Kit Translate
    implementation("com.google.mlkit:translate:16.1.2")
    implementation("com.google.mlkit:common:16.0.0")

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
