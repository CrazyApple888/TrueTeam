package me.idrew.main_cards.domain.location

import me.idrew.main_cards.domain.model.LonLat

interface LocationService {

    fun observeLocationUpdates(locationConsumer: (LonLat) -> Unit)
}