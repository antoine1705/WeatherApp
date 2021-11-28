import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.toannguyen.weatherapp"
        minSdk = 21
        targetSdk = 31
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
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    configurations {
        all {
            exclude(group = "com.google.guava", module = "listenablefuture")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":domain"))
    implementation(project(":data"))
    testImplementation(project(":tools-test", "testArtifacts"))

    implementation(Coroutines.core)
    implementation(Coroutines.android)

    implementation(Kotlin.stdlib)

    implementation(Retrofit.runtime)
    implementation(Retrofit.gson)
    implementation(Retrofit.okhttp_logging_interceptor)

    implementation(Navigation.navigation_ui)
    implementation(Navigation.fragment_ktx)
    implementation(Navigation.runtime_ktx)

    implementation(Koin.Android)
    implementation(Koin.AndroidScope)
    implementation(Koin.AndroidViewModel)

    implementation(Common.constraint_layout)
    implementation(Common.core_ktx)
    implementation(Common.timber)
    implementation(Common.recyclerview)
    implementation(Support.app_compat)

    implementation(Room.runtime)

    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation(Common.junit)
}