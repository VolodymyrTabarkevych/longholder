package com.traday.longholder.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report")
data class ReportEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "currencyCode") val currencyCode: String,
    @ColumnInfo(name = "profit") val profit: Double,
    @ColumnInfo(name = "allMoney") val allMoney: Double,
    @ColumnInfo(name = "countOfCrypto") val countOfCrypto: Double,
    @ColumnInfo(name = "percents") val percents: Double,
    @ColumnInfo(name = "priceNow") val priceNow: Double,
    @ColumnInfo(name = "coinName") val coinName: String,
    @Embedded(prefix = "crypto") val cryptoMoney: CryptoEntity,
    @ColumnInfo(name = "dateOfReport") val dateOfReport: String
)