package settings

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.*

internal fun Project.publishSettings(
    extension: PublishingExtension,
) {
    val mavenGroupId: String by extra
    val mavenPublishUrl: String by extra

    extension.apply {
        publications.configureEach {
            if (this is MavenPublication) {
                groupId = mavenGroupId
            }
            repositories {
                maven(mavenPublishUrl) {
                    credentials {
                        username = System.getenv("MAVEN_USER") ?: "NONE"
                        password = System.getenv("MAVEN_PASSWORD") ?: "NONE"
                    }
                }
            }
        }
    }
}
