pluginManagement {
    includeBuild("remote-build-logic")
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://nexus.gtnewhorizons.com/repository/public/") {
            content {
                includeGroup("com.gtnewhorizons")
                includeGroupByRegex("com\\.gtnewhorizons\\..+")
            }
        }
    }
}

plugins {
    id("settings-setup")
}

rootProject.name = "Forgelin"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("remote-build-logic/libs.versions.toml"))
        }
    }
}
