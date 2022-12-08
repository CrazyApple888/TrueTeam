plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
}

dependencies {
    implementation(Dependencies.Kotlin.KOTLIN_STANDARD_LIBRARY)
    implementation(Dependencies.Koin.KOIN_ANDROID)
    implementation(Dependencies.NETWORK.LOGGING_INTERCEPTOR)
    implementation(Dependencies.NETWORK.RETROFIT)
    implementation(Dependencies.Gson.GSON)

    implementation(project(Modules.TOKEN))
}