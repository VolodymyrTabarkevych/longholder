package com.traday.longholder.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.traday.longholder.data.local.entity.UserEntity
import com.traday.longholder.data.local.entity.UserEntity.Companion.TABLE_NAME

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM $TABLE_NAME LIMIT 1")
    suspend fun getUser(): UserEntity

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteUser()
}