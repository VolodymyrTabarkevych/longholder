package com.traday.longholder.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.traday.longholder.data.local.entity.CryptoEntity
import com.traday.longholder.data.local.entity.CryptoEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao : BaseDao<CryptoEntity> {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getCryptos(): Flow<List<CryptoEntity>>

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    suspend fun deleteCryptoById(id: Int)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteCryptos()
}