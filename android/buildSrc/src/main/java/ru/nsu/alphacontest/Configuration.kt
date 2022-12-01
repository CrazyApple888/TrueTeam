import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.configure() {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = Settings.JVM_TARGET
    }
    plugins.whenPluginAdded {
        when (this) {
            is AppPlugin -> configureAppModule(project)
            is LibraryPlugin -> configureAndroidLibraryModule(project)
        }
    }
}

fun configureAppModule(project: Project) {
    project.configure<BaseExtension> {
        compileSdkVersion(Settings.COMPILE_SDK_VERSION)
        buildToolsVersion(Settings.BUILD_TOOLS_VERSION)
        defaultConfig {
            applicationId = Settings.APPLICATION_ID
            minSdk = Settings.MIN_SDK_VERSION
            targetSdk = Settings.TARGET_SDK_VERSION
            versionCode = Settings.VERSION_CODE
            versionName = Settings.VERSION_NAME
            multiDexEnabled = true
            vectorDrawables.useSupportLibrary = true
            javaCompileOptions.annotationProcessorOptions.arguments += Settings.roomIncrementalSettings
        }
        setBuildTypes()
        setJavaVersion()
        setExcludes()
    }
}

fun configureAndroidLibraryModule(project: Project) {
    project.configure<BaseExtension> {
        compileSdkVersion(Settings.COMPILE_SDK_VERSION)
        buildToolsVersion(Settings.BUILD_TOOLS_VERSION)
        defaultConfig {
            minSdk = Settings.MIN_SDK_VERSION
            targetSdk = Settings.TARGET_SDK_VERSION
            multiDexEnabled = true
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables.useSupportLibrary = true
            javaCompileOptions.annotationProcessorOptions.arguments += Settings.roomIncrementalSettings
        }
        setBuildTypes()
        setJavaVersion()
        setExcludes()
    }
}

fun BaseExtension.setBuildTypes() {
    buildTypes {
        getByName(Settings.BuildTypes.Release.NAME) {
            isMinifyEnabled = Settings.BuildTypes.Release.MINIFY_ENABLED
            proguardFiles(
                getDefaultProguardFile(Settings.BuildTypes.DEFAULT_PRO_GUARD_FILE),
                Settings.BuildTypes.PROGUARD_RULES
            )
        }
        getByName(Settings.BuildTypes.Debug.NAME) {
            isMinifyEnabled = Settings.BuildTypes.Debug.MINIFY_ENABLED
            proguardFiles(
                getDefaultProguardFile(Settings.BuildTypes.DEFAULT_PRO_GUARD_FILE),
                Settings.BuildTypes.PROGUARD_RULES
            )
        }
    }
}

fun BaseExtension.setJavaVersion() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

fun BaseExtension.setExcludes() {
    packagingOptions {
        resources.excludes.add("META-INF/*.kotlin_module")
    }
}