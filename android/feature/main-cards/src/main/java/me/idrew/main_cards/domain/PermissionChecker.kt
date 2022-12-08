package me.idrew.main_cards.domain

interface PermissionChecker {

    fun isPermissionLocationGranted(): Boolean
}