plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
}

dependencies {
    implementation(Dependencies.Kotlin.KOTLIN_STANDARD_LIBRARY)
    implementation(Dependencies.Coroutines.COROUTINES_ANDROID)
    implementation(Dependencies.Coroutines.COROUTINES_CORE)
    implementation(Dependencies.Koin.KOIN_ANDROID)
    implementation(Dependencies.CORE)
    implementation(Dependencies.APP_COMPAT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.VIEW_BINDING_DELEGATE)
    implementation(project(Modules.DESIGN))
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.TOKEN))
    implementation(project(Modules.UTILS))
    implementation(Dependencies.NETWORK.RETROFIT)
    implementation(Dependencies.Navigation.NAVIGATION_FRAGMENT)
    implementation(Dependencies.Gson.GSON)
}