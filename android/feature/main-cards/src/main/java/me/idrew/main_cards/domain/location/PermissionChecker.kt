package me.idrew.main_cards.domain.location

interface PermissionChecker {

    fun isPermissionLocationGranted(): Boolean
}