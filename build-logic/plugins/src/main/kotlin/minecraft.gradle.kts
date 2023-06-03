@file:Suppress("UnstableApiUsage")
import net.minecraftforge.gradle.common.BaseExtension

plugins {
    id("forge")
    kotlin("jvm")
}

configure<BaseExtension> {
    commonMinecraft(project)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val modName: String by extra

tasks.named<ProcessResources>("processResources") {
    inputs.property("version", project.version)
    inputs.property("mcversion", libs.versions.minecraftVersion.get())
    filesMatching("mcmod.info") {
        expand(
            "minecraftVersion" to libs.versions.minecraftVersion.get(),
            "modVersion" to modName,
            "modName" to modName,
        )
    }
}
