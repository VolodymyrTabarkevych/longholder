package com.traday.longholder.data.local.datasource.currency

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

interface ICurrencyLocalDataSource {

    suspend fun insertOrUpdateCurrencies(currencies: List<CurrencyEntity>): Result<List<CurrencyEntity>>

    suspend fun getCurrencies(): Result<List<CurrencyEntity>>

    fun subscribeOnCurrencies(): Flow<Result<List<CurrencyEntity>>>
}