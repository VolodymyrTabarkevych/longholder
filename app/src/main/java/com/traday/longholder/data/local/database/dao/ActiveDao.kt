package com.traday.longholder.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.traday.longholder.data.local.entity.ActiveEntity
import com.traday.longholder.data.local.entity.ActiveEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ActiveDao : BaseDao<ActiveEntity> {

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getActives(): List<ActiveEntity>

    @Query("SELECT * FROM $TABLE_NAME")
    fun subscribeOnActives(): Flow<List<ActiveEntity>>

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    suspend fun deleteActiveById(id: Int)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteActive()
}