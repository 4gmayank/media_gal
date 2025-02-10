plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.galleryview"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.galleryview"
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.picasso)
    implementation (libs.glide)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Compose things
    implementation (libs.androidx.activity.compose) // Jetpack Compose Activity
    implementation (libs.androidx.ui) // Jetpack Compose UI
    //noinspection GradleDependency
    implementation (libs.androidx.material3) // Material Design 3 (Optional)
    //noinspection GradleDependency
    implementation (libs.androidx.ui.tooling.preview)
    //noinspection UseTomlInstead
    implementation ("androidx.compose.foundation:foundation:1.7.7")
    implementation (libs.androidx.material)
}