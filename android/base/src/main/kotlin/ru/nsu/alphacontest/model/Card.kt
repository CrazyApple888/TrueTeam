package ru.nsu.alphacontest.model

class Card(
    val name: String,
    val category: CardCategory,
    val barcode: Barcode,
)

class Barcode(
    val type: BarcodeType,
    val number: String,
)

sealed interface CardCategory {

    val stringValue: String
    val localizedValue: String

    companion object {

        fun forName(name: String): CardCategory = when (name) {
            "cafe" -> Cafe
            "restaurant" -> Restaurant
            "pharmacy" -> Pharmacy
            "grocery" -> Grocery
            "optics" -> Optics
            "clothing" -> Clothing
            "fueling_station" -> FuelingStation
            else -> Company
        }

        fun forLocalizedName(name: String): CardCategory = when (name) {
            "Кафе" -> Cafe
            "Ресторан" -> Restaurant
            "Аптека" -> Pharmacy
            "Супермаркет" -> Grocery
            "Оптика" -> Optics
            "Одежда" -> Clothing
            "Заправочная станция" -> FuelingStation
            else -> Company
        }
    }

    object Cafe : CardCategory {

        override val stringValue: String = "cafe"
        override val localizedValue: String = "Кафе"
    }

    object Restaurant : CardCategory {

        override val stringValue: String = "restaurant"
        override val localizedValue: String = "Ресторан"
    }

    object Pharmacy : CardCategory {

        override val stringValue: String = "pharmacy"
        override val localizedValue: String = "Аптека"
    }

    object Grocery : CardCategory {

        override val stringValue: String = "grocery"
        override val localizedValue: String = "Супермаркет"
    }

    object Optics : CardCategory {

        override val stringValue: String = "optics"
        override val localizedValue: String = "Оптика"
    }

    object Clothing : CardCategory {

        override val stringValue: String = "clothing"
        override val localizedValue: String = "Одежда"
    }

    object FuelingStation : CardCategory {

        override val stringValue: String = "fueling_station"
        override val localizedValue: String = "Заправочная станция"
    }

    object Company : CardCategory {

        override val stringValue: String = "company"
        override val localizedValue: String = "Компания"
    }
}