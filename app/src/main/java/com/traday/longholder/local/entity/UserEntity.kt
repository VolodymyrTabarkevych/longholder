package com.traday.longholder.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "userName") val userName: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "currencyCode") val currencyCode: String/*,
    @Embedded(prefix = "crypto") val cryptos: List<CryptoEntity>,
    @Embedded(prefix = "message") val messages: List<MessageEntity>,
    @Embedded(prefix = "report") val reports: List<ReportEntity>*/
)