package com.traday.longholder.data.local.datasource.crypto

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.CryptoEntity
import kotlinx.coroutines.flow.Flow

interface ICryptoLocalDataSource {

    suspend fun saveOrUpdateCryptos(vararg: CryptoEntity): Result<Unit>

    suspend fun saveOrUpdateCryptos(cryptos: List<CryptoEntity>): Result<Unit>

    fun getCryptos(): Flow<Result<List<CryptoEntity>>>

    suspend fun deleteCrypto(id: Int): Result<Unit>

    suspend fun deleteCryptos(): Result<Unit>
}