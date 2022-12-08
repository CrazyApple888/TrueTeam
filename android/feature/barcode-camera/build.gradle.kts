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
    implementation(Dependencies.Camera.CAMERA_X_CORE)
    implementation(Dependencies.Navigation.NAVIGATION_UI)
    implementation(Dependencies.Navigation.NAVIGATION_FRAGMENT)
    implementation(Dependencies.Camera.CAMERA_X_VIEW)
    implementation(Dependencies.Camera.CAMERA_X_LIFECYCLE)
    implementation(Dependencies.Camera.CAMERA_X_EXT)
    implementation(Dependencies.Barcode.BARCODE_SCANNER)

    implementation(project(Modules.DESIGN))
    implementation(project(Modules.BASE))
}