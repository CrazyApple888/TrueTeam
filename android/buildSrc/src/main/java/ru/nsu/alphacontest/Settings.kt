object Settings {

    const val COMPILE_SDK_VERSION = 32
    const val BUILD_TOOLS_VERSION = "30.0.3"

    const val APPLICATION_ID = "ru.nsu.alphacontest"
    const val MIN_SDK_VERSION = 23
    const val TARGET_SDK_VERSION = 33
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val JVM_TARGET = "11"

    val roomIncrementalSettings = "room.incremental" to "true"

    object BuildTypes {

        object Release {

            const val NAME = "release"
            const val MINIFY_ENABLED = true
        }

        object Debug {

            const val NAME = "debug"
            const val MINIFY_ENABLED = false
        }

        const val DEFAULT_PRO_GUARD_FILE = "proguard-android-optimize.txt"
        const val PROGUARD_RULES = "proguard-rules.pro"
    }
}