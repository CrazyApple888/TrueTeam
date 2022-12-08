package me.idrew.main_cards.data.model

import com.google.gson.annotations.SerializedName

class OrderedCardsResponse(
    @SerializedName("nearestCards")
    val nearestCards: List<CardResponse>,
    @SerializedName("anotherCards")
    val anotherCards: List<CardResponse>,
)

class CardResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("number")
    val number: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("barcodeType")
    val barcodeType: String,
)