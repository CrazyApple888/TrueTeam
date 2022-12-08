package ru.nsu.wallet.service

import org.springframework.stereotype.Service
import ru.nsu.wallet.entity.OrderedCards
import ru.nsu.wallet.entity.Card
import ru.nsu.wallet.exception.GeoApiException
import ru.nsu.wallet.service.geo.gis.GeoApiService
import ru.nsu.wallet.utils.TitleFormatter.formatName
import java.util.TreeSet

@Service
class OrderCardService(private val geoApiService: GeoApiService) {

    @Throws(GeoApiException::class)
    fun orderByDistance(
        lon: Double,
        lat: Double,
        type: String,
        cards: MutableList<Card>,
        allCards: MutableList<Card>
    ): OrderedCards {
        val nearestCompanyList = geoApiService.getNearestCompanies(lon, lat, type)

        /*todo тут возможно нужна сортировка nearestCompanyList по координатам,
            но вроде api 2gis отдает в отсортированном виде*/

        /*todo позор вонючий, переписать по нормальному*/
        val nearestCards = LinkedHashSet<Card>()
        for (nearestCompany in nearestCompanyList.result.items) {
            val formattedName = formatName(nearestCompany.name)

            for (card in cards) {
                if (formattedName.contains(formatName(card.name), true)) {
                    nearestCards.add(card)
                }
            }
        }

        allCards.removeAll(nearestCards)
        cards.sortBy { card -> card.name }

        return OrderedCards(
            nearestCards = nearestCards.toList(),
            anotherCards = allCards
        )
    }
}
