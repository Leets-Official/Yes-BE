import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    kotlin("plugin.jpa") version "2.1.0"
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

dependencies {
    implementation(project(":module-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")
}
