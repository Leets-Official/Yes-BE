import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false

dependencies {
    implementation(project(":module-domain"))
    implementation(project(":module-infrastructure:persistence-db"))
    implementation(project(":module-independent"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
}

tasks.getByName<BootJar>("bootJar") {
    mainClass.set("site.yourevents.YourEventsApplicationKt")
}
