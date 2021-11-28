import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
    id("org.jetbrains.dokka") version "1.6.0"
    id("maven-publish")
    id("signing")
}

group = "com.chromasgaming"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-client-core:1.6.2")
    implementation("io.ktor:ktor-client-cio:1.6.2")
    implementation("io.ktor:ktor-client-serialization:1.6.2")
    implementation("io.ktor:ktor-client-auth:1.6.2")
    implementation("io.ktor:ktor-client-logging:1.6.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")
}

publishing {
    publications {
        create<MavenPublication>("ktweet") {
            groupId = "com.chromasgaming"
            artifactId = "ktweet"

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ChromasIV/KTweet")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("TOKEN")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    onlyIf { !project.hasProperty("skipTests")}
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
