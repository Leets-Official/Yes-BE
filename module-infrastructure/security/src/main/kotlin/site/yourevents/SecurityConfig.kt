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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import site.yourevents.filter.CustomExceptionHandleFilter
import site.yourevents.filter.JwtAuthenticationEntryPoint
import site.yourevents.filter.JwtAuthorizationFilter
import site.yourevents.jwt.CustomAccessDeniedHandler
import site.yourevents.jwt.JwtProvider


@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${app.server.url}")
    private val serverUrl: String,

    @Value("\${management.endpoints.web.base-path}")
    private val actuatorEndPoint: String,

    private val jwtProvider: JwtProvider,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
) {
    @Bean
    fun SecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/swagger-ui/**", permitAll)
                authorize("/v3/**", permitAll)
                authorize("/health-check", permitAll)
                authorize("$actuatorEndPoint/**", permitAll)
                authorize(anyRequest, permitAll)
            }
        }

        http {
            csrf { disable() }
            formLogin { disable() }
            httpBasic { disable() }
            sessionManagement { SessionCreationPolicy.STATELESS }
            cors { corsConfigurationSource() }
        }

        http {
            exceptionHandling {
                accessDeniedHandler = customAccessDeniedHandler
                authenticationEntryPoint = jwtAuthenticationEntryPoint
            }
        }

        http {
            oauth2Login { }
        }

        http {
            addFilterBefore<UsernamePasswordAuthenticationFilter>(
                filter = JwtAuthorizationFilter(jwtProvider)
            )
            addFilterBefore<JwtAuthorizationFilter>(
                filter = CustomExceptionHandleFilter()
            )
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
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf(
                "http://localhost:3000",
                "https://www.yourevents.site",
                "https://yourevents.site",
                serverUrl
            )
            allowedMethods = listOf("POST", "GET", "PATCH", "DELETE", "OPTIONS")
            allowedHeaders = listOf("*")
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
