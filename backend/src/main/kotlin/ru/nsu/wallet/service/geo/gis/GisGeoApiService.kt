package ru.nsu.wallet.service.geo.gis

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import ru.nsu.wallet.configuration.properties.GeoApiProperties
import ru.nsu.wallet.exception.GeoApiException
import ru.nsu.wallet.service.geo.gis.answer.NearestCompanyAnswer
import java.io.File

@Service
class GisGeoApiService(
    private val restTemplate: RestTemplate,
    private val geoApiProperties: GeoApiProperties,
    private val objectMapper: ObjectMapper
) : GeoApiService {

    companion object {
        private val LOGGER = KotlinLogging.logger {}
    }

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
            val start = System.currentTimeMillis()
//            val result = restTemplate.getForObject(
//                "${geoApiProperties.url}/3.0/items?" +
//                        "key={key}&" +
//                        "type={type}&" +
//                        "q={q}&" +
//                        "sort={sort}&" +
//                        "sort_point={sort_point}&" +
//                        "locale={locale}&" +
//                        "fields={fields}",
//                NearestCompanyAnswer::class.java, params
//            ) ?: throw GeoApiException("Невалидный ответ от geo api")

            val result = objectMapper.readValue(
                File(geoApiProperties.mockResponsePath).readText(),
                NearestCompanyAnswer::class.java
            )


            val end = System.currentTimeMillis()
            LOGGER.info { "Remote api request time: ${end - start} ms" }
            LOGGER.info { "Remote api answer: $result" }

            return result
        } catch (exception: RestClientException) {
            throw GeoApiException("Невалидный ответ от geo api")
        }
    }
}
