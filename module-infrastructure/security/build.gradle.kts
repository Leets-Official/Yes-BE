import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation(project(":module-independent"))
    implementation(project(":module-domain"))

    //Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")

    //OAuth 2.0
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    //JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // Spring Security Test
    testImplementation("org.springframework.security:spring-security-test")

    // JSON
    implementation("com.google.code.gson:gson:2.11.0")
}
