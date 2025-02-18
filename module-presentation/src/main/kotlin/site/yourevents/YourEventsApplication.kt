package site.yourevents

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class YourEventsApplication

fun main(args: Array<String>) {
    runApplication<YourEventsApplication>(*args)
}
