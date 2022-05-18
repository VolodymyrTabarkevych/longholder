package com.traday.longholder.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.traday.longholder.data.local.entity.CurrencyEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "linkToPhoto") val linkToPhoto: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "indexOnExchange") val indexOnExchange: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "dateOfUpdate") val dateOfUpdate: String,
    @ColumnInfo("symbol") val symbol: String
) {

    companion object {

        const val TABLE_NAME = "currency"
    }
}