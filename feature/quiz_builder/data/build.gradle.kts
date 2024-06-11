@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.dev.bayan.ibrahim.feature.quiz_builder_data"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
//            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = rootProject.extra["javaVersion"] as JavaVersion
        targetCompatibility = rootProject.extra["javaVersion"] as JavaVersion
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = rootProject.extra["jvmTarget"] as String
    }
    buildToolsVersion = rootProject.extra["buildToolsVersion"] as String

}

dependencies {
    api(project(":core:data"))
    api(project(":core:database"))
    api(project(":core:datastore"))
    api(project(":core:network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    // dagger hilt
    implementation(libs.androidx.hilt.navigation)
    implementation(libs.google.dagger.hilt)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.google.dagger.hiltCompiler)

    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)

    // every module depends on this module must implement this compiler in its gradle.build file
    ksp(libs.androidx.room.compiler)

    coreLibraryDesugaring(libs.android.tools.desugaring)

    // room test
    testImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.room.testing)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.jetbrains.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.espresso.core)

    api(libs.jetbrains.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    coreLibraryDesugaring(libs.android.tools.desugaring)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(libs.jetbrains.kotlinx.collections)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    coreLibraryDesugaring(libs.android.tools.desugaring)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    coreLibraryDesugaring(libs.android.tools.desugaring)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(libs.androidx.appcompat)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.navigation)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.material3.android)
    api(libs.androidx.material3.android)
    api(libs.androidx.ui.android)
    api(libs.androidx.ui.tooling.preview.android)
    api(libs.google.android.material)
    api(libs.jetbrains.kotlinx.collections)
    api(libs.google.accompanist.adaptive)
    coreLibraryDesugaring(libs.android.tools.desugaring)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    // hilt
    implementation(libs.google.dagger.hilt)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.google.dagger.hiltCompiler)

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // hilt
    implementation(libs.google.dagger.hilt)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.google.dagger.hiltCompiler)

    implementation(libs.jetbrains.kotlinx.datetime)
    implementation(libs.jetbrains.kotlinx.serialization.json)

    // pagination:
    implementation(libs.androidx.paging.runtime)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    coreLibraryDesugaring(libs.android.tools.desugaring)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // dagger hilt
//    implementation(libs.androidx.hilt.lifecycle.viewModel)
    implementation(libs.androidx.hilt.navigation)
    implementation(libs.google.dagger.hilt)

    implementation(libs.jetbrains.kotlinx.datetime)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.google.dagger.hiltCompiler)

    // pagination:
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    coreLibraryDesugaring(libs.android.tools.desugaring)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // hilt
    implementation(libs.google.dagger.hilt)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.google.dagger.hiltCompiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.android.material)

    coreLibraryDesugaring(libs.android.tools.desugaring)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}