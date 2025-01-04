package site.yourevents.config.swagger

import DisableSwaggerSecurity
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod

@Configuration
class SwaggerConfig {

    @Value("\${app.server.url}")
    private lateinit var serverUrl: String

    @Bean
    fun openAPI(): OpenAPI {
        val jwtScheme = "JWT"

        val securityRequirement = SecurityRequirement().addList(jwtScheme)
        val components = Components().addSecuritySchemes(
            jwtScheme,
            SecurityScheme()
                .name(jwtScheme)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat(jwtScheme)
        )

        return OpenAPI()
            .addServersItem(Server().url(serverUrl))
            .components(components)
            .info(apiInfo())
            .addSecurityItem(securityRequirement)
    }

    @Bean
    fun operationCustomizer(): OperationCustomizer {
        return OperationCustomizer { operation: Operation, handlerMethod: HandlerMethod ->
            val methodAnnotation = handlerMethod.getMethodAnnotation(DisableSwaggerSecurity::class.java)
            if (methodAnnotation != null) {
                operation.security = emptyList()
            }
            operation
        }
    }

    private fun apiInfo(): Info {
        return Info()
            .title("YES API")
            .description("우리 만날래? Yes!")
            .version("1.0.0")
    }
}
