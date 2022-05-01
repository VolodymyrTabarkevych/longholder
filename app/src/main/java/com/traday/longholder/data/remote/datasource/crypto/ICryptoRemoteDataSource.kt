package com.traday.longholder.data.remote.datasource.crypto

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.remote.dto.CryptoDto

interface ICryptoRemoteDataSource {

    suspend fun getCryptos(): Result<List<CryptoDto>>

    suspend fun createCrypto(crypto: CryptoDto): Result<Unit>

    suspend fun updateCrypto(crypto: CryptoDto): Result<Unit>

    suspend fun deleteCrypto(id: Int): Result<Unit>
}