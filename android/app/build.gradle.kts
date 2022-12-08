plugins {
    id(Plugins.ANDROID_APPLICATION)
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
    implementation(project(Modules.LOGIN))
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.TOKEN))
    implementation(project(Modules.REGISTRATION))
    implementation(Dependencies.Navigation.NAVIGATION_FRAGMENT)
    implementation(Dependencies.Navigation.NAVIGATION_UI)
    implementation(project(Modules.BASE))
    implementation(project(Modules.CARDS))
    implementation(project(Modules.BARCODE_CAMERA))
}
