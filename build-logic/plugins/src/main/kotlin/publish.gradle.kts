@file:Suppress("DSL_SCOPE_VIOLATION")

import groovy.lang.Closure
import groovy.util.Node
import groovy.util.NodeList
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    kotlin("jvm")
    id("com.palantir.git-version")
    `maven-publish`
    `java-library`
}

val modId: String by extra
val modName: String by extra
val modGroup: String by extra
group = modGroup

var versionOverride: String? = System.getenv("VERSION") ?: null
var identifiedVersion = runCatching {
    val gitVersion: Closure<String> by extra
    versionOverride ?: gitVersion()
}.getOrElse {
    "unknown".also {
        versionOverride = it
    }
}

version = identifiedVersion
val modVersion = identifiedVersion

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

archivesName.set(modId)

tasks.withType<GenerateModuleMetadata> {
    enabled = false
    mustRunAfter("reobf")
}

configure<PublishingExtension> {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifact(tasks["shadowJar"])
            pom.withXml {
                val pomNode = asNode()

                val dependencyNodes: NodeList = pomNode.get("dependencies") as NodeList
                dependencyNodes.forEach {
                    (it as Node).parent().remove(it)
                }
            }
            groupId = modGroup
            artifactId = modId
            version = identifiedVersion
        }
    }
    repositories {
        maven {
            url = uri("https://maven.accident.space/repository/maven-releases/")
            credentials {
                username = System.getenv("MAVEN_USER") ?: "NONE"
                password = System.getenv("MAVEN_PASSWORD") ?: "NONE"
            }
        }
    }
}

