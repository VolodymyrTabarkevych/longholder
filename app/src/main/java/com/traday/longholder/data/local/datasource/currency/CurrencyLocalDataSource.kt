package com.traday.longholder.data.local.datasource.currency

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.database.dao.CurrencyDao
import com.traday.longholder.data.local.entity.CurrencyEntity
import com.traday.longholder.data.mapper.flowResult
import com.traday.longholder.data.mapper.result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyLocalDataSource @Inject constructor(
    private val currencyDao: CurrencyDao
) : ICurrencyLocalDataSource {

    override suspend fun insertOrUpdateCurrencies(currencies: List<CurrencyEntity>): Result<List<CurrencyEntity>> =
        result {
            currencyDao.deleteCurrencies()
            currencyDao.insertData(currencies)
            currencyDao.getCurrencies()
        }

    override suspend fun getCurrencies(): Result<List<CurrencyEntity>> =
        result { currencyDao.getCurrencies() }

    override fun subscribeOnCurrencies(): Flow<Result<List<CurrencyEntity>>> =
        flowResult { currencyDao.subscribeOnCurrencies() }
}