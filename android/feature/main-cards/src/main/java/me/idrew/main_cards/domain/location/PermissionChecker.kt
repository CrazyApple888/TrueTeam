package me.idrew.main_cards.domain.location

interface PermissionChecker {

    fun isLocationPermissionGranted(): Boolean

    fun isCameraPermissionGranted(): Boolean
}