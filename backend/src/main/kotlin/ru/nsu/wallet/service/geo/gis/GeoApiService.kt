package ru.nsu.wallet.service.geo.gis

import ru.nsu.wallet.exception.GeoApiException

interface GeoApiService {

    @Throws(GeoApiException::class)
    fun getNearestCompanies(lon: Double, lat: Double, type: String) : NearestCompanyAnswer
}