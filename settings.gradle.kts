pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        // JitPack for libraries hosted there (e.g. MPAndroidChart)
        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // JitPack is required to resolve com.github.PhilJay:MPAndroidChart
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "NariNexus"
include(":app")
