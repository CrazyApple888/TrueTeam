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
    implementation(Dependencies.Navigation.NAVIGATION_FRAGMENT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.Recycler.RECYCLER_VIEW)
    implementation(Dependencies.VIEW_BINDING_DELEGATE)
    implementation(Dependencies.NETWORK.RETROFIT)
    implementation(Dependencies.Gson.GSON)

    implementation(project(Modules.DESIGN))
    implementation(project(Modules.BASE))
    implementation(project(Modules.DATABASE))
    implementation(project(Modules.ADD_CARD))
    implementation(project(Modules.NETWORK))
}