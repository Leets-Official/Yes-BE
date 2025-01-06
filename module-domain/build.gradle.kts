import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    kotlin("plugin.jpa") version "2.1.0"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
