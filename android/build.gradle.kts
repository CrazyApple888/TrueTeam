buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.GRADLE)
        classpath(Dependencies.Kotlin.KOTLIN)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subProjects {
    this.configure()
}

fun subProjects(vararg folders: Any, action: Action<in Project>) {
    folders.forEach { if (project.path == it.toString()) return }
    subprojects(action)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}