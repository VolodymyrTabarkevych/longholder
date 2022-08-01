package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.datasource.currency.ICurrencyLocalDataSource
import com.traday.longholder.data.local.entity.CurrencyEntity
import com.traday.longholder.data.mapper.toEntity
import com.traday.longholder.data.remote.datasource.currency.ICurrencyRemoteDataSource
import com.traday.longholder.data.remote.dto.CurrencyDto
import com.traday.longholder.domain.repository.ICurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyRemoteDataSource: ICurrencyRemoteDataSource,
    private val currencyLocalDataSource: ICurrencyLocalDataSource
) : ICurrencyRepository {

    override suspend fun getCurrencies(sync: Boolean): Result<List<CurrencyEntity>> {
        if (!sync) return currencyLocalDataSource.getCurrencies()
        val remoteCurrenciesResult = currencyRemoteDataSource.getCurrencies()
        val localCurrenciesResult = currencyLocalDataSource.getCurrencies()

        return when (remoteCurrenciesResult) {
            is Result.Error -> {
                if (localCurrenciesResult is Result.Success && localCurrenciesResult.data.isNotEmpty()) {
                    localCurrenciesResult
                } else {
                    remoteCurrenciesResult
                }
            }
            is Result.Success -> {
                val mappedCurrencies =
                    remoteCurrenciesResult.data.mapIndexed { index, currencyDto ->
                        currencyDto.toEntity(
                            index
                        )
                    }
                currencyLocalDataSource.insertOrUpdateCurrencies(mappedCurrencies)
            }
        }
    }

    override suspend fun getUserCurrencies(): Result<List<CurrencyDto>> {
        return currencyRemoteDataSource.getUserCurrencies()
    }

    override fun subscribeOnCurrencies(syncAtStart: Boolean): Flow<Result<List<CurrencyEntity>>> {
        return currencyLocalDataSource.subscribeOnCurrencies()
            .onStart {
                if (!syncAtStart) return@onStart
                val remoteCurrenciesResult = currencyRemoteDataSource.getCurrencies()
                if (remoteCurrenciesResult is Result.Success) {
                    val mappedCurrencies =
                        remoteCurrenciesResult.data.mapIndexed { index, currencyDto ->
                            currencyDto.toEntity(
                                index
                            )
                        }
                    currencyLocalDataSource.insertOrUpdateCurrencies(mappedCurrencies)
                } else if (remoteCurrenciesResult is Result.Error) {
                    emit(remoteCurrenciesResult)
                }
            }
    }
}