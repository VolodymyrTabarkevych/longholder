package com.traday.longholder.data.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.datasource.currency.ICurrencyLocalDataSource
import com.traday.longholder.data.local.entity.CurrencyEntity
import com.traday.longholder.data.mapper.toEntity
import com.traday.longholder.data.remote.datasource.currency.ICurrencyRemoteDataSource
import com.traday.longholder.domain.repository.ICurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyRemoteDataSource: ICurrencyRemoteDataSource,
    private val currencyLocalDataSource: ICurrencyLocalDataSource
) : ICurrencyRepository {

    override fun getCurrencies(): Flow<Result<List<CurrencyEntity>>> {
        return currencyLocalDataSource.getCurrencies()
            .onStart {
                val remoteResult = currencyRemoteDataSource.getCurrencies()
                if (remoteResult is Result.Success) {
                    val mappedItems =
                        remoteResult.data.mapIndexed { index, currencyDto ->
                            currencyDto.toEntity(
                                index
                            )
                        }
                    currencyLocalDataSource.saveOrUpdateCurrencies(mappedItems)
                } else if (remoteResult is Result.Error) {
                    emit(remoteResult)
                }
            }
    }
}