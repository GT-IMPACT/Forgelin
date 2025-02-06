import com.gtnewhorizons.retrofuturagradle.MinecraftExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import settings.minecraftSettings
import settings.publishSettings

class SetupMinecraftPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.kotlin.jvm.get().pluginId)
            apply("com.gtnewhorizons.retrofuturagradle")
            apply("com.gtnewhorizons.gtnhconvention")
            apply("com.github.johnrengelman.shadow")
        }
        extensions.configure<MinecraftExtension>(::minecraftSettings)
    }
}

class SetupPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.kotlin.jvm.get().pluginId)
            apply(libs.plugins.shadow.get().pluginId)
            apply(libs.plugins.gitversion.get().pluginId)
            apply("maven-publish")
            apply("java-library")
        }
        extensions.configure<PublishingExtension>(::publishSettings)
    }
}

fun DependencyHandler.implementation(dependencyNotation: Any) =
    add("implementation", dependencyNotation)

val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()
