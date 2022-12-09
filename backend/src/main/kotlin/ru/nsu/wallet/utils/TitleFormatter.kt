package ru.nsu.wallet.utils

object TitleFormatter {
    fun formatCategory(category: String) =
        category.lowercase()
            .trim()

    fun formatName(name: String) =
        name.lowercase()
            .trim()
            .replace('ё', 'е', true)
}
