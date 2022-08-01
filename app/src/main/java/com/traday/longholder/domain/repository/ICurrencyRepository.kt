package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.CurrencyEntity
import com.traday.longholder.data.remote.dto.CurrencyDto
import kotlinx.coroutines.flow.Flow

interface ICurrencyRepository {

    suspend fun getCurrencies(sync: Boolean): Result<List<CurrencyEntity>>

    suspend fun getUserCurrencies(): Result<List<CurrencyDto>>

    fun subscribeOnCurrencies(syncAtStart: Boolean): Flow<Result<List<CurrencyEntity>>>
}