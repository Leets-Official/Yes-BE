import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    kotlin("plugin.jpa") version "2.1.0"
}

dependencies {

    // Sentry
    api("io.sentry:sentry-logback:7.17.0")
    api("com.github.maricn:logback-slack-appender:1.6.1")
}
