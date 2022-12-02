package ru.nsu.wallet.service.geo.gis

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import ru.nsu.wallet.configuration.properties.GeoApiProperties
import ru.nsu.wallet.exception.GeoApiException

@Service
class GisGeoApiService(
    private val restTemplate: RestTemplate,
    private val geoApiProperties: GeoApiProperties
) : GeoApiService {

    @Throws(GeoApiException::class)
    override fun getNearestCompanies(lon: Double, lat: Double, type: String): NearestCompanyAnswer {
        val params = mapOf(
            "key" to geoApiProperties.key,
            "type" to "branch",
            "q" to type,
            "sort" to "distance",
            "sort_point" to "$lon,$lat",
            "locale" to "ru_RU",
            "fields" to "items.point",
        )

        try {
            val result = restTemplate.getForObject(
                "${geoApiProperties.url}/3.0/items?" +
                        "key={key}&" +
                        "type={type}&" +
                        "q={q}&" +
                        "sort={sort}&" +
                        "sort_point={sort_point}&" +
                        "locale={locale}&" +
                        "fields={fields}",
                NearestCompanyAnswer::class.java, params
            ) ?: throw GeoApiException("Невалидный ответ от geo api")

            return result
        } catch (exception: RestClientException) {
            throw GeoApiException("Невалидный ответ от geo api")
        }
    }
}