package ru.nsu.wallet.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("geo-api")
data class GeoApiProperties(
    var url: String = "",
    var key: String = "",
    var mockResponsePath: String = "",
    var enableMockRequest: Boolean = true
)