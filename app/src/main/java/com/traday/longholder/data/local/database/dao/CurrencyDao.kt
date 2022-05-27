package com.traday.longholder.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.traday.longholder.data.local.entity.CurrencyEntity
import com.traday.longholder.data.local.entity.CurrencyEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao : BaseDao<CurrencyEntity> {

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM $TABLE_NAME")
    fun subscribeOnCurrencies(): Flow<List<CurrencyEntity>>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteCurrencies()
}