package settings

import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate

fun Project.getVersionMod(): String {

    val versionDetails: Closure<VersionDetails> by extra
    val details = versionDetails()

    val versionOverride: String? = System.getenv("VERSION") ?: null

    val identifiedVersion: String = runCatching {
        versionOverride ?: if (System.getenv("CI") != null) details.lastTag else details.version
    }.getOrDefault("unknown")

    return identifiedVersion
}
