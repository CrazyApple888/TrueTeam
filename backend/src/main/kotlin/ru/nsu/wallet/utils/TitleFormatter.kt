package ru.nsu.wallet.utils

object TitleFormatter {
    fun formatType(type: String) =
        type.lowercase()
            .trim()

    fun formatName(name: String) =
        name.lowercase()
            .trim()
            .replace('ё', 'e', true)
}