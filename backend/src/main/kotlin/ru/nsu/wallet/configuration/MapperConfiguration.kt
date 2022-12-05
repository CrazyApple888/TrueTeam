package ru.nsu.wallet.configuration

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapperConfiguration {

    @Bean
    fun getObjectMapper(): ObjectMapper {
        return ObjectMapper()
            .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
            .configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
            .registerKotlinModule()
    }
}
