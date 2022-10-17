import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

fun PluginDependenciesSpec.androidApp(): PluginDependencySpec = id("com.android.application")

fun PluginDependenciesSpec.kotlinAndroid(): PluginDependencySpec = kotlin("android")

fun PluginDependenciesSpec.kotlinKapt(): PluginDependencySpec = kotlin("kapt")

fun PluginDependenciesSpec.kotlinExtension(): PluginDependencySpec = kotlin("android.extensions")

fun PluginDependenciesSpec.dagger(): PluginDependencySpec = id("dagger.hilt.android.plugin")

fun PluginDependenciesSpec.library(): PluginDependencySpec = id("com.android.library")

fun PluginDependenciesSpec.googleService(): PluginDependencySpec =
    id("com.google.gms.google-services")

fun PluginDependenciesSpec.firebaseCrashlytics(): PluginDependencySpec =
    id("com.google.firebase.crashlytics")

fun PluginDependenciesSpec.androidGitVersion(): PluginDependencySpec {
    return id("com.gladed.androidgitversion") version "0.4.14"
}

fun PluginDependenciesSpec.xdimen(): PluginDependencySpec = id("io.github.islamkhsh.xdimen")
