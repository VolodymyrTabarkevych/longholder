package com.traday.longholder.domain.repository

import com.traday.longholder.data.base.Result
import com.traday.longholder.data.local.entity.CryptoEntity
import com.traday.longholder.data.remote.dto.CryptoDto
import kotlinx.coroutines.flow.Flow

interface ICryptoRepository {

    fun getCryptos(): Flow<Result<List<CryptoEntity>>>

    suspend fun createCrypto(crypto: CryptoDto): Result<Unit>

    suspend fun updateCrypto(crypto: CryptoDto): Result<Unit>

    suspend fun deleteCrypto(id: Int): Result<Unit>
}