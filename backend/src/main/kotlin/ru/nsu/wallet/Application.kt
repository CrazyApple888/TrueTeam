package ru.nsu.wallet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@EnableConfigurationProperties
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}