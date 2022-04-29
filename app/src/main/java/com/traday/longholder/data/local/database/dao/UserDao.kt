package com.traday.longholder.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.traday.longholder.data.local.entity.UserEntity

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity

    @Query("DELETE FROM user")
    suspend fun deleteUser()
}