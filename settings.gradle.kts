pluginManagement {
//    includeBuild("build_logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "JaAr"
include(":app")
/*
 * all features depends on one or more core module "it may use a part of data base of make a network call"
 * core components is independent on each other and modules,
 * app module depends on feature module only.
 */

// core:
include(":core:common")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:ui")

// features:
include(":feature:library:data")
include(":feature:library:ui")

include(":feature:quiz_builder:data")
include(":feature:quiz_builder:domain")
include(":feature:quiz_builder:ui")

include(":feature:quiz_runner:data")
include(":feature:quiz_runner:domain")
include(":feature:quiz_runner:ui")

include(":feature:statistics:data")
include(":feature:statistics:domain")
include(":feature:statistics:ui")
include(":feature:library:domain")
include(":core:data")
