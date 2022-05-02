package com.traday.longholder.data.local.datasource.currency

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

interface ICurrencyLocalDataSource {

    suspend fun saveOrUpdateCurrencies(vararg: CurrencyEntity): Result<Unit>

    suspend fun saveOrUpdateCurrencies(currencies: List<CurrencyEntity>): Result<Unit>

    fun getCurrencies(): Flow<Result<List<CurrencyEntity>>>

    suspend fun deleteCurrencies(): Result<Unit>
}