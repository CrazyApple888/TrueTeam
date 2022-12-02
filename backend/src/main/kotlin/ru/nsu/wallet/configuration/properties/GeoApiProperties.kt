package ru.nsu.wallet.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("geocoder")
data class GeoApiProperties(
    var url: String = "",
    var key: String = ""
)