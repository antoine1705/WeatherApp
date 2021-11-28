object Versions {
    const val arch_core = "2.1.0"
    const val room = "2.4.0-beta02"
    const val support = "1.4.0"
    const val koin = "2.1.3"
    const val junit = "4.13"
    const val retrofit = "2.9.0"
    const val okhttp_logging_interceptor = "3.9.0"
    const val constraint_layout = "2.1.2"
    const val timber = "5.0.1"
    const val android_gradle_plugin = "3.2.0"
    const val coroutinesVersion = "1.3.2"
    const val kotlin = "1.2.51"
    const val recycler_view = "1.2.1"
    const val navigation = "2.3.5"
    const val core_ktx = "1.7.0"
    const val mockk = "1.11.0"
}

//def build_versions =[:]
//build_const
//val min_sdk = 14
//build_const
//val target_sdk = 26
//build_const
//val build_tools = "27.0.3"
//ext.build_versions = build_versions
//

object Support {
    const val app_compat = "androidx.appcompat:appcompat:${Versions.support}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val testing = "android.arch.persistence.room:testing:${Versions.room}"
    const val coroutines = "androidx.room:room-ktx:${Versions.room}"
}

object ArchCore {
    const val testing = "androidx.arch.core:core-testing:${Versions.arch_core}"
}

object Retrofit {
    const val runtime = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp_logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor}"
}

object Koin {
    const val Android = "org.koin:koin-android:${Versions.koin}"
    const val AndroidScope = "org.koin:koin-android-scope:${Versions.koin}"
    const val AndroidViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    const val core = "org.koin:koin-core:${Versions.koin}"
    const val core_ext = "org.koin:koin-core-ext:${Versions.koin}"
}

object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlin}"
    const val test = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
}

object Navigation {
    const val runtime_ktx = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
    const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
}

object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"
}

object Common {
    const val constraint_layout =
        "com.android.support.constraint:constraint-layout:${Versions.constraint_layout}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val junit = "junit:junit:${Versions.junit}"
    const val android_gradle_plugin =
        "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recycler_view}"
}

