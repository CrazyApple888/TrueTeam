package me.idrew.main_cards.data

import android.annotation.SuppressLint
import android.location.LocationManager
import me.idrew.main_cards.domain.location.LocationService
import me.idrew.main_cards.domain.model.LonLat

class LocationServiceImpl(
    private val locationManager: LocationManager
): LocationService {

    @SuppressLint("MissingPermission")
    override fun observeLocationUpdates(locationConsumer: (LonLat) -> Unit) {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 100L, 0F
        ) { location ->
            locationConsumer(
                LonLat(
                    location.longitude.toString(),
                    location.latitude.toString()
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun getLastKnownLocation(): LonLat {
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).let {
            LonLat(
                lon = it?.longitude?.toString() ?: "0",
                lat = it?.latitude?.toString() ?: "0"
            )
        }
    }
}