package me.idrew.main_cards.data

import android.annotation.SuppressLint
import android.location.LocationManager
import me.idrew.main_cards.domain.LocationService
import me.idrew.main_cards.domain.LonLat

class LocationServiceImpl(
    private val locationManager: LocationManager
): LocationService {

    @SuppressLint("MissingPermission")
    override fun observeLocationUpdates(locationConsumer: (LonLat) -> Unit) {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 1000L, 50F
        ) { location ->
            locationConsumer(
                LonLat(
                    location.longitude.toString(),
                    location.latitude.toString()
                )
            )
        }
    }
}