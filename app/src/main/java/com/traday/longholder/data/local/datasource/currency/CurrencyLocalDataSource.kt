package com.traday.longholder.data.local.datasource.currency

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.database.dao.CurrencyDao
import com.traday.longholder.data.local.entity.CurrencyEntity
import com.traday.longholder.extensions.safeFlow
import com.traday.longholder.extensions.safeOperation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyLocalDataSource @Inject constructor(
    private val currencyDao: CurrencyDao
) : ICurrencyLocalDataSource {

    override suspend fun saveOrUpdateCurrencies(vararg: CurrencyEntity): Result<Unit> =
        safeOperation {
            currencyDao.insertData(vararg)
        }

    override suspend fun saveOrUpdateCurrencies(currencies: List<CurrencyEntity>): Result<Unit> =
        safeOperation {
            currencyDao.insertData(currencies)
        }

    override fun getCurrencies(): Flow<Result<List<CurrencyEntity>>> =
        safeFlow { currencyDao.getCurrencies() }

    override suspend fun deleteCurrencies(): Result<Unit> =
        safeOperation { currencyDao.deleteCurrencies() }
}