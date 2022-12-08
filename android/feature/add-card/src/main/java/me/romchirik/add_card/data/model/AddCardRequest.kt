package me.romchirik.add_card.data.model

import com.google.gson.annotations.SerializedName

data class AddCardRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("barcodeType")
    val barcodeType: String,

    @SerializedName("number")
    val number: String,
)