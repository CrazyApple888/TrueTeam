object Dependencies {

    object Versions {
        const val KOTLIN = "1.6.20"
        const val GRADLE = "7.1.2"
        const val LIFECYCLE = "2.4.0"
        const val MATERIAL = "1.5.0"
        const val CORE = "1.7.0"
        const val APP_COMPAT = "1.4.1"
        const val ROOM = "2.4.1"
        const val NAVIGATION = "2.5.3"
        const val GSON = "2.9.1"
        const val KOIN = "3.3.0"
        const val COROUTINES = "1.6.0"
    }

    const val GRADLE = "com.android.tools.build:gradle:${Versions.GRADLE}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"

    object Kotlin {
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0"
        const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN}"
        const val KOTLIN_STANDARD_LIBRARY =
            "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.KOTLIN}"
    }

    object Lifecycle {

        const val LIFECYCLE_RUNTIME =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
        const val LIFECYCLE_VIEWMODEL =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    }

    object Koin {

        const val KOIN_ANDROID = "io.insert-koin:koin-android:${Versions.KOIN}"
    }

    object Room {
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    }

    object Coroutines {

        const val COROUTINES_CORE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
        const val COROUTINES_ANDROID =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    }

    object Gson {
        const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
    }

    object Navigation {
        const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
        const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    }

    const val VIEW_BINDING_DELEGATE = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.6"
}