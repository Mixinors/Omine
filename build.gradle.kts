plugins {
    application

    // Fabric Loom
    id("fabric-loom") version "0.9-SNAPSHOT"

    // Java Virtual Machine
    kotlin("jvm") version "1.5.21"

    // KotlinX Serialization
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.21"

    // Maven Publish
    id("maven-publish")
}

group = "dev.vini2003"
version = "0.1.0"

minecraft {
    accessWidener = file("src/main/resources/mod.accesswidener")
}

repositories {
    // Maven Central
    mavenCentral()

    // Fabric
    maven("https://maven.fabricmc.net/")

    // JitPack
    maven("https://jitpack.io")

    // Ladysnake Mods
    maven("https://ladysnake.jfrog.io/artifactory/mods")
}

dependencies {
    // Minecraft
    minecraft(
        group = "com.mojang",
        name = "minecraft",
        version = "1.17.1"
    )

    // Yarn
    mappings(
        group = "net.fabricmc",
        name = "yarn",
        version = "1.17.1+build.32",
        classifier = "v2"
    )

    // Fabric Loader
    modImplementation(
        group = "net.fabricmc",
        name = "fabric-loader",
        version = "0.11.6"
    )

    // Fabric API
    modImplementation(
		group = "net.fabricmc.fabric-api",
		name = "fabric-api",
		version = "0.39.2+1.17"
    )

    // Fabric Language Kotlin
    modImplementation(
		group = "net.fabricmc",
		name = "fabric-language-kotlin",
		version = "1.6.3+kotlin.1.5.21"
    )

    // Cardinal Components API Base
    modImplementation(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-base",
		version = "3.1.1"
    )

    include(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-base",
		version = "3.1.1"
    )

    // Cardinal Components API Block
    modImplementation(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-block",
		version = "3.1.1"
    )

    include(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-block",
		version = "3.1.1"
    )

    // Cardinal Components API Chunk
    modImplementation(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-chunk",
		version = "3.1.1"
    )

    include(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-chunk",
		version = "3.1.1"
    )

    // Cardinal Components API Entity
    modImplementation(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-entity",
		version = "3.1.1"
    )

    include(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-entity",
		version = "3.1.1"
    )

    // Cardinal Components API Base
    modImplementation(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-item",
		version = "3.1.1"
    )

    include(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-item",
		version = "3.1.1"
    )

    // Cardinal Components API Level
    modImplementation(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-level",
		version = "3.1.1"
    )

    include(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-level",
		version = "3.1.1"
    )

    // Cardinal Components API Scoreboard
    modImplementation(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-scoreboard",
		version = "3.1.1"
    )

    include(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-scoreboard",
		version = "3.1.1"
    )

    // Cardinal Components API World
    modImplementation(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-world",
		version = "3.1.1"
    )

    include(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-world",
		version = "3.1.1"
    )

    // Cardinal Components API Util
    modImplementation(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-util",
		version = "3.1.1"
    )

    include(
		group = "io.github.onyxstudios.Cardinal-Components-API",
		name = "cardinal-components-util",
		version = "3.1.1"
    )


    // KotlinX Serialization Json
    implementation(
		group = "org.jetbrains.kotlinx",
		name = "kotlinx-serialization-json",
		version = "1.2.2"
    )

    // KotlinX Coroutines
    implementation(
		group = "org.jetbrains.kotlinx",
		name = "kotlinx-coroutines-jdk8",
		version = "1.5.1"
    )

    // KotlinX DateTime
    implementation(
		group = "org.jetbrains.kotlinx",
		name = "kotlinx-datetime",
		version = "0.2.1"
    )
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.getByName<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") {
        expand(
		    mutableMapOf(
		        "id" to "template",
		        "name" to "Template",
		        "version" to "0.1.0",
		        "description" to "This is a template mod!",
		        "minecraftVersion" to "1.17.1",
		        "kotlinVersion" to "1.15.21",
		        "fabricLoaderVersion" to "0.11.6",
		        "fabricApiVersion" to "0.39.2+1.17"
		    )
        )
    }
}

publishing {
    publications {
        create<MavenPublication>(name) {
		    this.groupId = group as String
		    this.artifactId = name
		    this.version = version
    
		    from(components["java"])
    
		    val sourcesJar by tasks.creating(Jar::class) {
		        val sourceSets: SourceSetContainer by project
    
		        from(sourceSets["main"].allJava)
		        classifier = "sources"
		    }
		    val javadocJar by tasks.creating(Jar::class) {
		        from(tasks.get("javadoc"))
		        classifier = "javadoc"
		    }
    
		    artifact(sourcesJar)
		    artifact(javadocJar)
        }
    }
}