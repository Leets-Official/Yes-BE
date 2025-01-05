package site.yourevents.sentry

import io.sentry.Sentry
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class SentryConfig(
    @Value("\${sentry.dsn}") private val sentryDsn: String,
    @Value("\${sentry.environment}") private val environment: String,
    @Value("\${sentry.servername}") private val serverName: String
) {
    @PostConstruct
    fun initSentry() {
        Sentry.init { options ->
            options.dsn = sentryDsn
            options.environment = environment
            options.serverName = serverName
        }
    }
}
