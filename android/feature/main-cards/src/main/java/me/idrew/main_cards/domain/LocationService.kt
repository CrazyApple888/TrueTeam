package me.idrew.main_cards.domain

interface LocationService {

    fun observeLocationUpdates(locationConsumer: (LonLat) -> Unit)
}