import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.buildconfig)
    alias(libs.plugins.shadow)
    id("minecraft")
    id("publish")
}

val modId: String by extra
val modName: String by extra
val modGroup: String by extra
val modAdapter: String by extra

buildConfig {
    packageName("$modGroup.$modId")
    buildConfigField("String", "MODID", "\"${modId}\"")
    buildConfigField("String", "MODNAME", "\"${modName}\"")
    buildConfigField("String", "VERSION", "\"${project.version}\"")
    buildConfigField("String", "GROUPNAME", "\"${modGroup}\"")
    buildConfigField("String", "MODADAPTER", "\"${modAdapter}\"")
    useKotlinOutput { topLevelConstants = true }
}

dependencies {
    shadow(libs.kotlin.stdlib)
    shadow(libs.kotlin.reflect)
    shadow(libs.kotlin.annotations)
    shadow(libs.kotlin.coroutines.core)
    shadow(libs.kotlin.coroutines.core.jvm)
}

tasks {
    shadowJar {
        mustRunAfter("reobf")
        archiveClassifier.set("")
        mergeServiceFiles()
    }
    build {
        dependsOn(shadowJar)
    }
}
