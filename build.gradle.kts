// Common Values for Modules
extra["compileSdk"] = 34
extra["minSdk"] = 21
extra["versionCode"] = 15
extra["versionName"] =
    "0.3.0-alpha01" // x.y.z; x major improvement (add or remove feature) y minor improvements, edit feature, z patch version
extra["targetSdk"] = 34
extra["buildToolsVersion"] = "34.0.0"
extra["jvmTarget"] = "17"
extra["javaVersion"] = JavaVersion.VERSION_17
extra["kotlinCompilerExtensionVersion"] = "1.5.13"
extra["androidGradlePluginVersion"] = "8.4.0"

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}
true // Needed to make the Suppress annotation work for the plugins block