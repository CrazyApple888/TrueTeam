package ru.nsu.wallet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ServletComponentScan
@EnableJpaRepositories
@EnableConfigurationProperties
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}