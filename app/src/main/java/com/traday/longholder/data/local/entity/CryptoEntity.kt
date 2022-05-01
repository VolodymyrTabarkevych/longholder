package com.traday.longholder.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crypto")
data class CryptoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "valueOfCrypto") val valueOfCrypto: Double,
    @ColumnInfo(name = "cryptoPriceOnStart") val cryptoPriceOnStart: Double,
    @ColumnInfo(name = "cryptoPriceOnEnd") val cryptoPriceOnEnd: Double,
    @ColumnInfo(name = "priceInOtherCurrencyOnStart") val priceInOtherCurrencyOnStart: Double,
    @ColumnInfo(name = "priceInOtherCurrencyOnEnd") val priceInOtherCurrencyOnEnd: Double,
    @ColumnInfo(name = "currentCurrencyPrice") val currentCurrencyPrice: Double,
    @ColumnInfo(name = "nameOfCurrency") val nameOfCurrency: String?,
    @ColumnInfo(name = "dateOfStart") val dateOfStart: String,
    @ColumnInfo(name = "dateOfEnd") val dateOfEnd: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "linkToImage") val linkToImage: String?,
    @ColumnInfo(name = "earnedMoney") val earnedMoney: Double,
    @ColumnInfo(name = "percents") val percents: Double
) {

    companion object {

        const val TABLE_NAME = "crypto"
    }
}