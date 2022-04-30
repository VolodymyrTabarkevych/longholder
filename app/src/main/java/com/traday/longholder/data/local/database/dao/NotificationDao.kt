package com.traday.longholder.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.traday.longholder.data.local.entity.NotificationEntity
import com.traday.longholder.data.local.entity.NotificationEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao : BaseDao<NotificationEntity> {

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getNotifications(): List<NotificationEntity>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteNotifications()

    @Query("SELECT COUNT(*) FROM $TABLE_NAME WHERE isRead = :isRead")
    fun getCountByIsRead(isRead: Boolean): Flow<Int>

    @Query("UPDATE $TABLE_NAME SET isRead = :isRead")
    suspend fun setIsReadForAll(isRead: Boolean)
}