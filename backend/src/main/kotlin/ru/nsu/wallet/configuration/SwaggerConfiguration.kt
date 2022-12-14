package ru.nsu.wallet.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .info(info())
    }

    private fun info(): Info {
        return Info().title("Wallet backend service")
            .description("Сервис для получения приоритезованного списка скидочных карт")
    }
}
