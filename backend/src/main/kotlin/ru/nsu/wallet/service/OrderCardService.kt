package ru.nsu.wallet.service

import org.springframework.stereotype.Service
import ru.nsu.wallet.entity.Card
import ru.nsu.wallet.exception.GeoApiException
import ru.nsu.wallet.service.geo.gis.GeoApiService

@Service
class OrderCardService(private val geoApiService: GeoApiService) {

    @Throws(GeoApiException::class)
    fun orderByDistance(lon: Double, lat: Double, type: String, cards: List<Card>): List<Card> {
//        val result = ArrayList<Card>()


//        val nearestCompanyList = geoApiService.getNearestCompanies(lon, lat, type)

        /*todo тут возможно нужна сортировка nearestCompanyList по координатам,
            но вроде api 2gis отдает в отсортированном виде*/

        return cards
    }
}
