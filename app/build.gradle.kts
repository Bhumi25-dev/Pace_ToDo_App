plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.okay.paceto_doapp"
    compileSdk = 36

    // 1. Standardize compileSdk for Android 14

    defaultConfig {
        applicationId = "com.okay.paceto_doapp"

        // 2. Lower minSdk to allow installation
        minSdk = 26

        // 3. Align targetSdk with Android 14
        targetSdk = 34

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
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.litert.support.api)
    implementation(libs.androidx.compose.runtime)

    //extended icons for material 3
    implementation("androidx.compose.material:material-icons-extended")

    val hiltVersion = "2.59.2"
    val navVersion = "2.10.0"
    val lifecycleVersion = "2.11.0"
    val hiltNavVersion = "1.4.0"

    // 1. Dagger Hilt (Core Dependency Injection)
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // 2. Jetpack Navigation for Compose
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // 3. Lifecycle & ViewModel for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    // 4. Hilt Navigation Integration (Crucial for Scoping)
    // This allows you to scope a ViewModel to a specific Navigation Graph route
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavVersion")

    val roomVersion = "2.8.4"

    // 1. Room Core and Runtime
    implementation("androidx.room:room-runtime:$roomVersion")

    // 2. Room KTX (Crucial for Kotlin Coroutines and Flow support)
    implementation("androidx.room:room-ktx:$roomVersion")

    // 3. Room Compiler (Uses KSP instead of KAPT)
    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("com.github.skydoves:cloudy:0.5.0")

    //Google Icons Library
    implementation("androidx.compose.material:material-icons-core")

    //datastore preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    val kizitonwose_version = "2.6.0"
    //calendar screen
    implementation("com.kizitonwose.calendar:view:$kizitonwose_version")
    // The compose calendar library for Android
    implementation("com.kizitonwose.calendar:compose:$kizitonwose_version")

    // Room Test helpers
    testImplementation("androidx.room:room-testing:${roomVersion}")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}