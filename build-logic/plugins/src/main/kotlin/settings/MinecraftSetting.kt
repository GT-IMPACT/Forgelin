@file:Suppress("UnstableApiUsage")

package settings

import com.gtnewhorizons.retrofuturagradle.MinecraftExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

internal fun Project.minecraftSettings(
    extension: MinecraftExtension,
) {
    val modName: String by extra

    with(extension) {
        groupsToExcludeFromAutoReobfMapping.addAll("com.diffplug", "com.diffplug.durian", "net.industrial-craft")
    }
}
