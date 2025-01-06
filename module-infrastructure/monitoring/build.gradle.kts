import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {

    // Sentry
    implementation("io.sentry:sentry-logback:7.17.0")
    implementation("com.github.maricn:logback-slack-appender:1.6.1")
}
