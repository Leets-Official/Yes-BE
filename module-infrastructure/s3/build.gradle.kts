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

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.0"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
}