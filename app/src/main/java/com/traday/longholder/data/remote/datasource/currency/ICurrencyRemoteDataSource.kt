package com.traday.longholder.data.remote.datasource.currency

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.dto.CurrencyDto

interface ICurrencyRemoteDataSource {

    suspend fun getCurrencies(): Result<List<CurrencyDto>>
}