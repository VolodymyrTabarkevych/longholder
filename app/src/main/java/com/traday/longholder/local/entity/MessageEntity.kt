package com.traday.longholder.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "valueOfMessage") val valueOfMessage: String,
    @ColumnInfo(name = "linkToTheImage") val linkToTheImage: String,
    @ColumnInfo(name = "dateOfSent") val dateOfSent: String,
    @ColumnInfo(name = "dateOfStart") val dateOfStart: String
)