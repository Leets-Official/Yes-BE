import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    kotlin("plugin.jpa") version "2.1.0"
}

dependencies {
    implementation(project(":module-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.0"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.780")
}