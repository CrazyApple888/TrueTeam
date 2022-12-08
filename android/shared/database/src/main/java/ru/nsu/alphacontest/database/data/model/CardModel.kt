package ru.nsu.alphacontest.database.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cards", indices = [Index(value = ["barcodeType", "barcodeNumber"], unique = true)])
data class CardModel(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "barcodeType") val barcodeType: String,
    @ColumnInfo(name = "barcodeNumber") val barcodeNumber: String,
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}