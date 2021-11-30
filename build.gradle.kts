import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
    id("org.jetbrains.dokka") version "1.6.0"
    id("maven-publish")
    id("signing")
    id("io.gitlab.arturbosch.detekt") version("1.19.0")
}

group = "com.chromasgaming"
version = "0.0.2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("io.ktor:ktor-client-core:1.6.4")
    implementation("io.ktor:ktor-client-cio:1.6.4")
    implementation("io.ktor:ktor-client-serialization:1.6.4")
    implementation("io.ktor:ktor-client-auth:1.6.4")
    implementation("io.ktor:ktor-client-logging:1.6.4")
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
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }

        }
    }
}

detekt {
    config = files("config/detekt/detekt-config.yml")
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
