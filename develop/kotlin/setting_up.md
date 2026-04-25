---
title: Setting Up a Kotlin project
description: Learn how to add Kotlin to your Minecraft mod.
authors:
  - MaxURhino
---

This page will walk you over, how to add the language Kotlin to your Minecraft mod.

## Differences in Gradle

There are some differences in the file names and file contents of the Gradle build files. For example, in Kotlin, every `.gradle` file also ends with `.kts`.

You need to change the name of only two files. Here are these:
- `build.gradle` -> `build.gradle.kts`
- `settings.gradle` -> `settings.gradle.kts`

If you have changed the file names of the build files, next you will need to tweak some things inside. First, you will need to import a class. Normal Gradle build files don't have imports, but Kotlin has.
You will need to import the class `org.jetbrains.kotlin.gradle.dsl.JvmTarget`. To import a class, you will first need to type in `import ` and then the class name. Like in Java, but without any semicolons (`;`).

In the entire file, you will need to replace all apostrophes (`'`) with quotes (`"`). In `plugins`, you will need to change all `id "plugin id" version "version"` with `id("plugin id") version "version"`.
Next, add this dependency:
```kts
id("org.jetbrains.kotlin.jvm") version "2.3.21"
```
Also, remove `base`. Next, in `repositories`, change all `maven { url "url/to/custom/maven" }` with `maven(url = "url/to/custom/maven")`. For more argument e.g. `name`, you will need to add an equals sign (`=`).
You will also need to replace all `gradle.properties` getters (e.g. `project.minecraft_version`) with e.g. `providers.gradleProperty("minecraft_version").get()`.

<sub><sup>Help me complete this tutorial</sup></sub>

The end result should look something like this:
`build.gradle.kts`:
```kts
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("net.fabricmc.fabric-loom") version providers.gradleProperty("loom_version").get()
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "2.3.21"
}

version = providers.gradleProperty("mod_version").get()
group = providers.gradleProperty("maven_group").get()

fabricApi {
    configureDataGeneration {
        client = true
    }
}

repositories {
  
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")
    implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")

    implementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_version").get()}")
    implementation("net.fabricmc:fabric-language-kotlin:${providers.gradleProperty("fabric_kotlin_version").get()}")
}

tasks.processResources {
    inputs.property("version", version)

    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 25
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_25
    }
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
    inputs.property("projectName", project.name)

    from("LICENSE") {
        rename { "${it}_${project.name}" }
    }
}

// configure the maven publication
publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
```
