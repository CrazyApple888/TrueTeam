package ru.nsu.wallet.service.geo.gis.answer

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Point(
    val lat: Double,
    val lon: Double
)
