package site.yourevents

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YourEventsApplication

fun main(args: Array<String>) {
    runApplication<YourEventsApplication>(*args)
}
