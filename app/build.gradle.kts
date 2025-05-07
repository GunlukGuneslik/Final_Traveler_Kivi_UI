plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.actualtravellerkiviprojectui"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.actualtravellerkiviprojectui"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }

    flavorDimensions += "mocking"
    productFlavors {
        create("prod") {
            // Uses Spring backend
            dimension = "mocking"
        }
        create("mock") {
            // Uses mocked data
            dimension = "mocking"
            applicationIdSuffix = ".mock"
            versionNameSuffix = "-mock"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}



dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.retrofit)
    implementation(libs.retrofit.jackson)
    implementation(libs.jackson)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.databind)
    implementation(libs.retrofit.mock)
    implementation(libs.jackson.datatype.jsr310)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}