import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.adishakti.repohunt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.adishakti.repohunt"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    val secretsPropertiesFile = rootProject.file("secret.properties")
    val secretsProperties = Properties()
    secretsProperties.load(FileInputStream(secretsPropertiesFile))
    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField("String", "GITHUB_API_KEY", secretsProperties["GITHUB_API_KEY"].toString())
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "GITHUB_API_KEY", secretsProperties["GITHUB_API_KEY"].toString())
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.compose.material.iconsExtended)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.webkit)

    kapt(libs.kotlin.kapt)

    implementation(libs.retrofit)
    implementation(libs.gson.convertor)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi.convertor)
    implementation(libs.moshi)
    implementation(libs.okhttp)
    implementation(libs.constraint.layout.compose)
    implementation(libs.system.ui.controller)

    //coil
    implementation(libs.coil)

    // room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
