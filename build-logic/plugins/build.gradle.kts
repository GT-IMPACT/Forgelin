plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.gitversion)
    implementation(libs.gradle.retro)
    implementation(libs.gradle.shadow)
    implementation(libs.gradle.forge) { isChanging = true }
    implementation(files(libs::class.java.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("minecraft-setup") {
            id = "minecraft-setup"
            implementationClass = "SetupMinecraftPlugin"
            version = "1.0.0"
        }
    }
    plugins {
        register("publish-setup") {
            id = "publish-setup"
            implementationClass = "SetupPublishPlugin"
            version = "1.0.0"
        }
    }
}
