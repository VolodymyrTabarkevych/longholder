package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

interface ICurrencyRepository {

    fun getCurrencies(): Flow<Result<List<CurrencyEntity>>>
}