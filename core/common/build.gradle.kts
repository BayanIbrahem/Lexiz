@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = rootProject.extra["javaVersion"] as JavaVersion
    targetCompatibility = rootProject.extra["javaVersion"] as JavaVersion
}
dependencies {
    api(libs.jetbrains.kotlinx.collections)
}