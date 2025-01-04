package site.yourevents

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${app.server.url}")
    private val serverUrl: String
) {

    @Bean
    fun SecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/swagger-ui/**", permitAll)
                authorize("/v3/**", permitAll)
                authorize("/health-check", permitAll)
                authorize(anyRequest, permitAll)
            }
            csrf { disable() }
            formLogin { disable() }
            httpBasic { disable() }
            sessionManagement { SessionCreationPolicy.STATELESS }
            // TODO: OAuth 설정
            // TODO: JJWT 설정
        }
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): (WebSecurity) -> Unit {
        return { web ->
            web.ignoring()
                .requestMatchers("/error", "/favicon.ico")
        }
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf(
            "http://localhost:3000",
            "https://www.yourevents.site",
            "https://yourevents.site",
            serverUrl
        )
        configuration.allowedMethods = listOf("POST", "GET", "PATCH", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
