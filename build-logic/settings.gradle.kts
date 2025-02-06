@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {

        maven("https://maven.accident.space/repository/maven-public/") {
            mavenContent {
                includeGroup("space.impact")
                includeGroupByRegex("space\\.impact\\..+")
            }
        }
        maven("https://nexus.gtnewhorizons.com/repository/public/") {
            mavenContent {
                includeGroup("com.gtnewhorizons")
                includeGroupByRegex("com\\.gtnewhorizons\\..+")
            }
        }
        maven("https://maven.minecraftforge.net")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
        mavenLocal()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

include(":plugins")
