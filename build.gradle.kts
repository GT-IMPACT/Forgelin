import settings.getVersionMod

plugins {
    alias(libs.plugins.setup.minecraft)
    alias(libs.plugins.setup.publish)
    id(libs.plugins.buildconfig.get().pluginId)
}

val modId: String by extra
val modName: String by extra
val modGroup: String by extra
val modAdapter: String by extra
val modVersion: String by extra

extra.set("modVersion", getVersionMod())

buildConfig {
    println(group)
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
    shadowImplementation(libs.kotlin.coroutines.core)
    shadowImplementation(libs.kotlin.coroutines.jvm)
}
