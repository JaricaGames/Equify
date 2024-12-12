plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.googleService)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.daggert)
    alias(libs.plugins.kapt)
    alias(libs.plugins.jetbrainsKotlinSerialization)
}

android {
    namespace = "com.jarica.compartirgastos"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jarica.compartirgastos"
        minSdk = 24
        targetSdk = 35
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

    //HILT
    implementation(libs.daggert)
    implementation(libs.androidx.runtime.livedata)
    kapt(libs.kapt)

    //NAVIGATION
    implementation(libs.navigation.compose)
    implementation(libs.kotlin.serialization.json)

    //FIREBASE
    implementation(platform(libs.firebase))
    implementation(libs.crashlytics)
    implementation(libs.daggert)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}