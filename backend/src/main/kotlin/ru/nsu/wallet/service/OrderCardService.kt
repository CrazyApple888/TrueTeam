package ru.nsu.wallet.service

import org.springframework.stereotype.Service
import ru.nsu.wallet.entity.Card
import ru.nsu.wallet.exception.GeoApiException
import ru.nsu.wallet.service.geo.gis.GeoApiService

@Service
class OrderCardService(private val geoApiService: GeoApiService) {

    @Throws(GeoApiException::class)
    fun orderByDistance(lon: Double, lat: Double, type: String, cards: MutableList<Card>): List<Card> {
        val nearestCompanyList = geoApiService.getNearestCompanies(lon, lat, type)

        /*todo тут возможно нужна сортировка nearestCompanyList по координатам,
            но вроде api 2gis отдает в отсортированном виде*/

        /*todo позор вонючий, переписать по нормальному*/
        val result = ArrayList<Card>()
        for (nearestCompany in nearestCompanyList.result.items) {
            for (card in cards)
                if (nearestCompany.name.contains(card.name, true)) {
                    result.add(card)
                }
        }

        cards.removeAll(result)
        cards.sortBy { card -> card.name }
        result.addAll(cards)

        return result
    }
}
