import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("minecraft-setup")
    id("publish-setup")
}

val modId = providers.gradleProperty("modId").get()
val modName = providers.gradleProperty("modName").get()
val modGroup = providers.gradleProperty("modGroup").get()
val modAdapter = providers.gradleProperty("modAdapter").get()
val modVersion = project.version.toString()

buildConfig {
    packageName(modGroup)
    buildConfigField("String", "MODID", "\"${modId}\"")
    buildConfigField("String", "MODNAME", "\"${modName}\"")
    buildConfigField("String", "VERSION", "\"${modVersion}\"")
    buildConfigField("String", "GROUPNAME", "\"${modGroup}\"")
    buildConfigField("String", "MODADAPTER", "\"${modAdapter}\"")
    useKotlinOutput { topLevelConstants = true }
}

dependencies {
    shadowImplementation(libs.kotlin.stdlib.core)
    shadowImplementation(libs.kotlin.stdlib.java8)
    shadowImplementation(libs.kotlin.reflect)
    shadowImplementation(libs.kotlin.annotations)
    shadowImplementation(libs.kotlin.coroutines.jvm)
    testImplementation(libs.test.junit)
}

tasks.named<ShadowJar>("shadowJar") {
    // Forge 1.7.10 scans every class with ASM 5, including multi-release entries.
    exclude("META-INF/versions/**", "module-info.class")
    exclude("META-INF/*.SF", "META-INF/*.RSA", "META-INF/*.DSA")
    filesMatching("META-INF/services/**") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
    mergeServiceFiles()
}

kotlin {
    compilerOptions.freeCompilerArgs.add("-Xjdk-release=8")
}

tasks.test {
    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(8))
    })
}
