import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation("com.google.zxing:javase:3.5.0")
    implementation("com.google.zxing:core:3.5.0")
}
