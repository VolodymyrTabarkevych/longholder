package com.traday.longholder.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.traday.longholder.data.local.entity.NotificationEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class NotificationEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "valueOfMessage") val valueOfMessage: String,
    @ColumnInfo(name = "linkToTheImage") val linkToTheImage: String,
    @ColumnInfo(name = "dateOfSent") val dateOfSent: String,
    @ColumnInfo(name = "dateOfStart") val dateOfStart: String,
    @ColumnInfo(name = "isRead") val isRead: Boolean
) {

    companion object {

        const val TABLE_NAME = "notification"
    }
}