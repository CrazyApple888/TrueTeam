package ru.nsu.wallet.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfiguration {

    @Bean
    fun getRestTemplate(): RestTemplate {
        return RestTemplate()
    }
}