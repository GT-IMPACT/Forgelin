# IMPACT Forgelin

![Latest Release](https://img.shields.io/github/v/tag/GT-IMPACT/Forgelin?label=Latest&sort=semver) ![Kotlin](https://img.shields.io/badge/Kotlin-2.3.21-8A2BE2)


Fork of [shadowfacts Forgelin](https://github.com/shadowfacts/Forgelin).

## Additions
- Backport to 1.7.10
- Shades the Kotlin standard library, runtime, coroutines-core, and reflect libraries so you don't have to.
- Provides a Forge `ILanguageAdapter` for using Kotlin `object` classes as your main mod class.

## Usage
```groovy
repositories {
    mavenCentral()
    maven {
        name = "impact"
        url = "https://maven.accident.space/repository/maven-public/"
    }
}

dependencies {
    implementation("space.impact:Forgelin:$version")
}
```

```kotlin
repositories {
    mavenCentral()
    maven("https://maven.accident.space/repository/maven-public/")
}

dependencies {
    implementation("space.impact:Forgelin:$version")
}
```

Forgelin bundles Kotlin 2.3.21 and kotlinx.coroutines 1.11.0. Avoid adding other
versions of these runtimes to the mods directory: Forge uses a shared classloader.
For Gradle consumers that use the Kotlin plugin, set
`kotlin.stdlib.default.dependency=false` in `gradle.properties` if Forgelin supplies
the runtime. Use `compileOnly` for any additional Kotlin/coroutines compile-time
dependencies and match Forgelin's versions.

## Building

Install JDK 25 for Gradle and JDK 8 for Minecraft and tests. Set `JAVA_HOME` to JDK 25.
The output targets Java 8. Dependency versions are defined in
`remote-build-logic/libs.versions.toml`.

```shell
git submodule update --init --recursive
./gradlew build
```

On Windows use `gradlew.bat build`. The unclassified JAR in `build/libs` is the
reobfuscated release artifact; `-dev.jar` is for development.

## Minecraft coroutine dispatchers

Import `net.shadowfacts.forgelin.coroutines.Client` or `.Server` to use
`Dispatchers.Client` / `Dispatchers.Server`. They run queued work at the end of
the corresponding tick. Work dispatched during queue processing, including
`yield()`, resumes on the next tick. Keep tick-thread work short and cancel
your coroutine scopes when the world/server stops; these dispatchers do not
manage mod-owned scopes. `Dispatchers.Client` is available only in the client.
