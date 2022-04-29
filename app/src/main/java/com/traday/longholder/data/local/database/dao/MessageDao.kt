package com.traday.longholder.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.traday.longholder.data.local.entity.MessageEntity

@Dao
interface MessageDao : BaseDao<MessageEntity> {

    @Query("SELECT * FROM message")
    suspend fun getMessages(): List<MessageEntity>
}