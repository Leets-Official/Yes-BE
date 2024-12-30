package site.yourevents

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
) {
    @Bean
    fun SecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
            csrf { disable() }
            formLogin { disable() }
            httpBasic { disable() }
            sessionManagement { SessionCreationPolicy.STATELESS }
            // TODO: OAuth 설정
            // TODO: JJWT 설정
            // TODO: CORS 설정
        }
        return http.build()
    }
}
